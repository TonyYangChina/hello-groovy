package cn.yami.helgro.service


/**
 * 查询系统日志接口
 * mins 2019-5-31 09:30:41
 */
interface SysLogService {

    def findLogByUserId(int userId);
}