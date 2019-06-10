package cn.yami.helgro.common.util

import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.lang3.StringUtils

/**
 *  fanwu 编写于 2017/3/6.
 */
@SuppressWarnings("GroovyUnusedDeclaration")
abstract class Validator {

    public static final int GAP = 100 * 60 * 1000
    //yyyy-MM-dd
    static final def DATE_PATTERN = ~/\d{4}-\d{2}-\d{2}/
    /**
     * 电话号码 校验
     */
    static boolean valitePhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return false
        }

        if (phone.length() != 11) {
            return false
        }

        if (!StringUtils.isNumeric(phone)) {
            return false
        }

        return true
    }


    static boolean validateError1(String secText) throws IllegalStateException {
        return !secText.find("code")
    }

    /**
     * 生成加密签名
     * @param requestBody
     * @param theKey
     */
    static String generateSign(Map<String, Object> requestBody, String theKey) {
        List<String> key = requestBody.keySet().toList().sort().findAll({ it != 'sign' })
        def paramStr = ""
        key.each { paramStr += it + requestBody.get(it).toString() }
        return DigestUtils.md5Hex(paramStr + theKey).toUpperCase()
    }


    static boolean validateBodySign(Map<String, Object> body, String key) {
        String sign = body.get("sign")
        if (StringUtils.isBlank(sign)) {
            return false
        }
        def expect = generateSign(body, key)
        return expect == sign
    }


    static boolean validateDateString(String dateString) throws IllegalArgumentException {
        if (!dateString) {
            return false
        }

        return DATE_PATTERN.matcher(dateString).matches()
    }

}