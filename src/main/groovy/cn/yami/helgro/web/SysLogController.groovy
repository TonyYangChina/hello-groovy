package cn.yami.helgro.web

import cn.yami.helgro.common.constant.Constants
import cn.yami.helgro.common.domain.SysLog
import cn.yami.helgro.db.User
import cn.yami.helgro.service.SysLogService
import cn.yami.helgro.service.UserService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest

/**
 * 日志查询类
 */
@Slf4j
@RestController
@RequestMapping("/sys_log")
class SysLogController {

    @Autowired
    UserService userService
    @Autowired
    SysLogService sysLogService

    // @Log("用户操作日志列表")
    @PostMapping(value = "/list", produces = "application/json")
    def logList(HttpServletRequest request, Model model) {
        def token = request.getHeader(Constants.Token_Header_Name)
        User user = userService.findUserByToken(token)

        List<SysLog> logs = sysLogService.findLogByUserId(user.id)
        return [ret: 0, msg: "OK",data:  logs.collect{[name: it.userName, id : it.id]}]
    }
}
