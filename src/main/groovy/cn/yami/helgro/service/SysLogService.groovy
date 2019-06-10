package cn.yami.helgro.service

import cn.yami.helgro.common.domain.SysLog


/**
 * 查询系统日志接口
 * mins 2019-5-31 09:30:41
 */
interface SysLogService {

    List<SysLog> findLogByUserId(int userId);
}