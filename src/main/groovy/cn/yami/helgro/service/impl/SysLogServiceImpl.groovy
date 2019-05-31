package cn.yami.helgro.service.impl

import cn.yami.helgro.common.domain.SysLog
import cn.yami.helgro.service.SysLogService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SysLogServiceImpl implements SysLogService{

    @Override
    @Transactional
    def findLogByUserId(int userId) {
        SysLog.findAllByUserId(userId)
    }
}
