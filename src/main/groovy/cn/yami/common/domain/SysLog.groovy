package cn.yami.common.domain

import grails.gorm.annotation.Entity
import groovy.transform.ToString

@Entity
@ToString(includeNames = true)
class SysLog {
    Integer id
    String username
    String operation
    Integer time
    String method
    String params
    String ip
    Date createTime
}
