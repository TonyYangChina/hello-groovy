package cn.yami.helgro.service

import cn.yami.helgro.db.User


/**
 * 范武(fanwu0618@gmail.com) 编写于 2017/3/3.
 */
interface UserService {

    /**
     * 发送注册/登录短信
     */
    def sendValidCodeSMS(String phone)

    /**
     * 注册、登录
     * @param phone 手机号码
     * @param validCode 验证码
     * @param deviceID 设备唯一识别码
     * @return token & uid
     */
    def login(String phone, String validCode, String deviceID)

    /**
     * 验证用户
     * @param uid UserID
     * @param token Token
     * @return 通过返回 true，错误返回 false
     */
    def validateToken(String uid, String token)

    /*根据token查找用户*/
    User findUserByToken(String token)

}