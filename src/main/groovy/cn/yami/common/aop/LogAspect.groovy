package cn.yami.common.aop

import cn.yami.common.annotation.Log
import cn.yami.common.domain.SysLog
import cn.yami.common.util.HttpContextUtil
import cn.yami.common.util.IPUtil
import groovy.util.logging.Slf4j
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.core.LocalVariableTableParameterNameDiscoverer
import org.springframework.stereotype.Component

import javax.servlet.http.HttpServletRequest
import java.lang.reflect.Method

@Slf4j
@Aspect
@Component
class LogAspect {

    @Pointcut("@annotation(cn.yami.common.annotation.Log)")
    void pointcut() {}


    @Around("pointcut()")
    Object logAround(ProceedingJoinPoint pjp) {
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
        // saveLog(point, time)
        analysis(pjp, time)
        return result
    }

    private void analysis(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature()
        Method method = signature.getMethod()
        SysLog sysLog = new SysLog()
        Log logAnnotation = method.getAnnotation(Log.class)
        if (logAnnotation != null) {
            // 注解上的描述
            sysLog.setOperation(logAnnotation.value())
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName()
        String methodName = signature.getName()
        sysLog.setMethod(className + "." + methodName + "()")
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
            sysLog.setParams(params)
        }
        // 获取request
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest()
        // 设置IP地址
        sysLog.setIp(IPUtil.getIpAddr(request))
        // 模拟一个用户名
        sysLog.setUsername("mrbird")
        sysLog.setTime((int) time)
        sysLog.setCreateTime(new Date())
        // 保存系统日志
        log.info(sysLog)
        // sysLogDao.saveSysLog(sysLog)
    }
}
