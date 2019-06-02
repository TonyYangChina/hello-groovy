package cn.yami.helgro.db

import grails.gorm.annotation.Entity
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

/**
 *  mins 2019-5-31 13:40:25
 */
@Entity
@SuppressWarnings("GroovyUnusedDeclaration")
@ToString
@EqualsAndHashCode
class Role implements Serializable{
    int id
    String name
    int status

    static belongsTo = User
    static hasMany = [user: User]

    // 这里设计成非级联User和User_Role表，仅由User_Role维护User和Role之间的关系
    /*User user
    static belongsTo = [user: User]

    static constraints = {
        user nullable: true
    }*/

    @SuppressWarnings("GroovyAssignabilityCheck")
    static mapping = {
        version false
    }
}
