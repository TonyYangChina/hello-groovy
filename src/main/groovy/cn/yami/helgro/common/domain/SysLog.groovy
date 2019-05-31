package cn.yami.helgro.common.domain

import grails.gorm.annotation.Entity
import groovy.transform.ToString
import lombok.EqualsAndHashCode

@Entity
@ToString
@EqualsAndHashCode
class SysLog {
    Integer id
    Integer userId
    String userName
    String operation
    Integer time
    String method
    String params
    String ip
    Date createTime

    // 定义表名 版本控制等
    static mapping = {
        version false
        userId column: 'user_id'
        userName column: 'user_name'
        createTime column: 'create_time'
    }

    // 约束条件
    static constraints = {
        operation nullable: true
        ip nullable: true
    }

}
