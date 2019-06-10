package cn.yami.helgro.common.constant

/**
 *  fanwu 编写于 2017/3/6.
 */
@SuppressWarnings("GroovyUnusedDeclaration")
abstract class Constants {
    /**
     * 公共服务的内部 token 仅用于测试及内部系统间内网调用
     */
    public static final String Common_TOKEN = "e24df12a81fd814017980d0c1fb2f968"

    /**
     * 认证短信数字长度
     */
    public static final int SMS_CODE_LENGTH = 6

    /**
     * Token 长度
     */
    public static final int TOKEN_LENGTH = 30

    /**
     * 用于加密的密钥
     */
    public static final String SEC_COMPANYCODE_SALT = "MACROSS"

    public static final String SEC_COMPANYCODE_SPLITER = "#"

    /**
     * Token Header Name
     */
    public static final String Token_Header_Name = "x-auth-token"

    /**
     * secID header name
     */
    public static final String SEC_ID_HEADER_NAME = "x-auth-secid"

    public static final long One_Hour_MilSec = 60 * 60 * 1000

    /**
     * JSON Content Type
     */
    public static final String JSON_CONTENT_TYPE = "application/json"


    public static final String Date_Warning = "当前查询日期早于服务确认日期"

    /** 数据不存在标志**/
    public static final String DATA_NO="-"

    public static final Integer DATA_NEGAIVE=-1

    public static final Integer DATA_ZERO=0


    /**会员系统的appid和签名**/
    public static  final Integer MEMBERDATASERVER_APPID=10011

    public static final String MEMBERDATASERVER_MD5KEY="d38f0f36bae62fdb13869710776f8e66"
}
