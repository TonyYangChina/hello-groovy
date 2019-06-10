package cn.yami.helgro.web

import cn.yami.helgro.common.constant.Constants
import cn.yami.helgro.common.constant.ErrorCode
import cn.yami.helgro.common.util.Validator
import cn.yami.helgro.db.User
import cn.yami.helgro.service.UserService
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletRequest


/**
 *  fanwu 编写于 2017/3/6.
 */
@Slf4j
@SuppressWarnings("GroovyUnusedDeclaration")
@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    private UserService userService



    @PostMapping(value = "/send_sms", produces = "application/json")
    sendValidMsg(@RequestBody Map<String, Object> body) {
        String phone = body.get("phone")
        if (!Validator.valitePhone(phone)) {
            return [ret: ErrorCode.INVALIDATE_PARAMETER_CODE, msg: "手机号码参数异常"]
        } else {
            return userService.sendValidCodeSMS(phone)
        }
    }

    @PostMapping(value = "/login", produces = "application/json")
    login(@RequestBody Map<String, Object> body) {
        // 手机、验证码、设备类型、
        String phone = body.get("phone")
        String validCode = body.get("valid_code")

        if (!Validator.valitePhone(phone)) {
            return [ret: ErrorCode.INVALIDATE_PARAMETER_CODE, msg: "手机号码：${phone} 参数异常".toString()]
        }
        if (StringUtils.isBlank(validCode) || validCode.length() != Constants.SMS_CODE_LENGTH) {
            return [ret: ErrorCode.INVALIDATE_PARAMETER_CODE, msg: "短信验证码：${validCode} 参数异常".toString()]
        }

        return userService.login(phone, validCode, " ")
    }

    @Transactional
    @PostMapping(value = "/role", produces = "application/json")
    def role(HttpServletRequest request) {
        def token = request.getHeader(Constants.Token_Header_Name)
        User user = userService.findUserByToken(token)

        // List<Role> roles = user.roles
        // return [ret: 0, msg: "OK", data: roles.toList().collect{[name : it.name]}]
        return [ret: 0, msg: "OK", data: '']

    }

    @Transactional
    @PostMapping(value = "/add", produces = "application/json")
    def add(HttpServletRequest request) {

        def user = new User(phone:'18906910522', name: '李四', status: 1)

       /* user.addToRoles(new Role(id: 10014, name: "指挥家", status: 0))
        user.addToRoles(new Role(id: 10015, name: "程序员", status: 0))*/
        user.save(flush: true)

        // def userRole = new UserRole()

        // user.addToUserRoles(userRole)

        return [ret: 0, msg: "OK", data: '']

    }

}
