package cn.yami.helgro.common.aop

import cn.yami.helgro.common.constant.Constants
import cn.yami.helgro.common.domain.SysLog
import cn.yami.helgro.common.annotation.Log
import cn.yami.helgro.common.util.HttpContextUtil
import cn.yami.helgro.common.util.IPUtil
import cn.yami.helgro.db.User
import cn.yami.helgro.service.UserService
import groovy.util.logging.Slf4j
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.LocalVariableTableParameterNameDiscoverer
import org.springframework.stereotype.Component

import javax.servlet.http.HttpServletRequest
import java.lang.reflect.Method

@Slf4j
@Aspect
@Component
class LogAspect {

    @Autowired
    UserService userService

    // @SuppressWarnings("GroovyUnusedDeclaration")
   /* @Pointcut("@annotation(Log)")
    void pointcut() {}*/


    @Around("@annotation(log)")
    Object logAround(ProceedingJoinPoint point, Log log) {
        Object result = null
        long beginTime = System.currentTimeMillis()
        try {
            // 执行方法
            result = point.proceed()
        } catch (Throwable e) {
            e.printStackTrace()
        }
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime
        // 保存日志
        saveLog(point, time)
        return result
    }

    /**
     * 日志保存
     * @param joinPoint
     * @param time
     */
    private void saveLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature()
        Method method = signature.getMethod()
        SysLog sysLog = new SysLog()
        Log logAnnotation = method.getAnnotation(Log.class)
        if (logAnnotation != null) {
            // 注解上的描述
            sysLog.operation = logAnnotation.value()
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName()
        String methodName = signature.getName()
        sysLog.method = className + "." + methodName + "()"
        // 请求的方法参数值
        Object[] args = joinPoint.getArgs()
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer()
        String[] paramNames = u.getParameterNames(method)
        if (args != null && paramNames != null) {
            String params = ""
            for (int i = 0; i < args.length; i++) {
                params += "  " + paramNames[i] + ": " + args[i]
            }
            sysLog.params = params
        }
        // 获取request
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest()
        // 获取token
        def token = request.getHeader(Constants.Token_Header_Name)
        User user = userService.findUserByToken(token)

        sysLog.ip = IPUtil.getIpAddr(request)
        sysLog.userId = user.id
        sysLog.userName = user.name
        sysLog.time = (int) time
        sysLog.createTime = new Date()

        // 使用GRail保存日志数据
        SysLog.withNewSession {
            sysLog.save(failOnError: true, flush: true)
        }
    }
}
