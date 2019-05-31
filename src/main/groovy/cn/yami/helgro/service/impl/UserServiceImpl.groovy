package cn.yami.helgro.service.impl

import cn.yami.helgro.common.constant.Constants
import cn.yami.helgro.common.constant.ErrorCode
import cn.yami.helgro.db.User
import cn.yami.helgro.service.UserService
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.StringUtils
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric

/**
 * 范武(fanwu0618@gmail.com) 编写于 2017/3/3.
 */
@SuppressWarnings("GroovyUnusedDeclaration")
@Component
@Slf4j
class UserServiceImpl implements UserService {
    public static final String Test_Phone = "13888888888"

    public static final String TEST_VALICODE = '888888'

    private static final String SMS_ID_SALT = "superman"

    @Autowired
    private SessionFactory sessionFactory

    private static final String DEFAULT_GRAY = "{\"gray\": false}"


    @Override
    @Transactional
    sendValidCodeSMS(String phone) {
        User user = User.findByPhoneAndStatus(phone, 1)
        if (!user) {
            return ErrorCode.User_Dont_Exist
        }
        String validateCode = randomNumeric(Constants.SMS_CODE_LENGTH)
        // 短信发送 暂时不考虑实现
        def resultMsg = [ret: 0, msg: "短信已发送，请查收", validCode: validateCode]
        if (resultMsg.ret == 0) {
            user.validCode = validateCode
            user.save()
        }
        return resultMsg
    }

    @Override
    @Transactional
    def login(String phone, String validCode, String deviceID) {
        User user = User.findByPhoneAndStatus(phone, 1)
        if (!user) {
            return ErrorCode.User_Dont_Exist
        }
        
        if (StringUtils.isBlank(user.validCode) || StringUtils.isBlank(validCode)) {
            if (validCode != TEST_VALICODE)
                return ErrorCode.Validate_Code_Error
        }

        if (!codeVerify(phone, validCode, user)) {
            return ErrorCode.Validate_Code_Error
        }

        Date now = new Date()

        if (now.getTime() - user.lastUpdated.getTime() > Constants.One_Hour_MilSec) {
            //当前时间与最后更新时间（短信发送时间）相差1小时以上
            if (phone != Test_Phone) //测试手机账号没有验证码过期
                return ErrorCode.Validate_Code_Outdated
        }

        String token = randomAlphanumeric(Constants.TOKEN_LENGTH)
        if (phone == Test_Phone) {
            token = "8" *Constants.TOKEN_LENGTH
        }
        user.token = token
        user.validCode = ""
        if (phone == Test_Phone) {
            user.validCode = TEST_VALICODE
        }
        user.save()

        return [ret: 0, access_token: token, data: [uid : user.id, name: user.name,
                                                    sex : user.sex, confirmation: user.confirmation,
                                                    type: user.type, phone: user.phone
        ]]

    }

    static boolean codeVerify(String phone, String validateCode, User user) {
        if (phone == Test_Phone && validateCode == "888888") {
            return true
        }
        return user.validCode.equalsIgnoreCase(validateCode)
    }


    @Override
    @Transactional
    validateToken(String uid, String token) {
        User user = User.findById(uid.toInteger())
        if (!user) {
            return new Tuple(false, ErrorCode.User_Dont_Exist)
        }

        if (user.status != 1) {
            return new Tuple(false, ErrorCode.User_Status_Error)
        }

        if (user.token != token) {
            return new Tuple(false, ErrorCode.Invalidate_Token)
        }
        return true
    }


    @Transactional
    @Override
    User findUserByToken(String token) {
        User.findByTokenAndStatus(token, 1)
    }
}
