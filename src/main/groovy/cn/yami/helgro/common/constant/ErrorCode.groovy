package cn.yami.helgro.common.constant

/**
 *  fanwu 编写于 2017/3/6.
 */
abstract class ErrorCode {

    public static final int User_Not_Exist_Code = 80000
    public static final int INVIDATE_TOKEN_CODE = 90000
    public static final int DECRYPT_ERROR_CODE = 90001
    public static final int RIGHTS_AUTH_CODE= 90002

    public static final int Validate_Code_Outdated_code = 80001
    /**
     * 手机号码格式异常
     */
    public static final int INVALIDATE_PARAMETER_CODE = 80002

    /**
     * 验证码不正确
     */
    public static final int INVALIDATE_VALIDATE_CODE = 80003

    public static final int SERVER_ERROR_CODE = 80004

    public static final int NO_DATA_ERROR_CODE = 80005

    private static final int User_Alredy_Confirmed_CODE = 80006

    private static final int User_Statues_Error_Code = 80007
    public static final int SERVER_RESPONSE_ERROR = 80008

//    int SECID_AUTHENTICATION_ERROR = 90001

    public static final Object User_Status_Error = [ret: User_Statues_Error_Code, msg: "您的账号已经停用，如有疑问请咨询相关人员"]

    public static final Object User_Dont_Exist = [ret: User_Not_Exist_Code, msg: "您的账号暂未开通，请联系相关人员开通"]

    public static final Object Validate_Code_Error = [ret: INVALIDATE_VALIDATE_CODE, msg: "您的验证码不正确"]

    public static final Object Validate_Code_Outdated = [ret: Validate_Code_Outdated_code, msg: "短信验证码超时，请重试"]

    public static final Object Invalidate_Token = [ret: INVIDATE_TOKEN_CODE, msg: "校验失败，请重新登录"]

    public static final Object No_Data_Response = [ret: NO_DATA_ERROR_CODE, msg: "暂无数据"]

    public static final Object User_Already_Confirmed = [ret: User_Alredy_Confirmed_CODE, msg: "用户已确认过协议"]

    public static final Object Company_Open_Time_Error = [ret: 80008, msg: "商家确认协议时间为空，无法查询数据"]


    public static final Object Only_Boss_Allow = [ret: 80009, msg: "只有老板类型用户才能操作"]

    public static final Object Not_Boss_Allow_update = [ret: 80010, msg: "抱歉，当前用户不允许修改"]

    public static final Object NOT_FIND_ROOMSTATE = [ret: 80011, msg: "抱歉，获取房态失败"]

    public static final Object INVALID_COMPANY_INFO = [ret: 80012, msg: "未知商家~"]

    public static final Object INVALID_ARGUMENT = [ret: 80014, msg: "参数非法"]

    public static final Object BIG_DATA_RPC_ERROR = [ret: 80015, msg: "请求大数据服务异常"]
}