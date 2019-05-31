package cn.yami.helgro.db

import grails.gorm.annotation.Entity
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 *  fanwu 编写于 2017/3/3.
 */
@Entity
@SuppressWarnings("GroovyUnusedDeclaration")
@ToString
@EqualsAndHashCode
class User {
    int id
    String phone
    String name
    int status
    String validCode
    String token
    Date dateCreated
    Date lastUpdated
    /**
     * 1 正式用户
     * 0 测试用户 ，默认值为0
     */
    int type
    int sex
    int confirmation
    /**
     * 用户手机类型 0 iOS 1 Android
     */
    int deviceType
    /**
     * 备注
     */
    String remark

    /**
     * 一对多关系
     */
    static hasMany = [userRoles: UserRole]

    List<Role> getRoles() {
        userRoles.collect{ it.role }.findAll{it.status==0}.toList()
    }


    @SuppressWarnings("GroovyAssignabilityCheck")
    static constraints = {
        validCode nullable: true
        token nullable: true
        remark nullable: true
    }

    @SuppressWarnings("GrUnresolvedAccess")
    static mapping = {
        version false
        titleRights lazy: true
        confirmation defaultValue: 0
        remark type: 'text'

    }

    def beforeInsert() {
        status = 1  //新增用户一定是有效用户，其实用户都是预置的
    }

}
