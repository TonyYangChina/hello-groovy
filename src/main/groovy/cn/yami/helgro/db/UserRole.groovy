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
class UserRole implements Serializable{
    /**
     * 关联的用户
     */
    //User user
    /**
     * 关联的角色
     */
    //Role role

    int status

   /* static mapping = {
        version false
        id composite: ['user', 'role']
        table 'user_role'
        user column: 'user_id'
        role column: 'role_id'
    }*/
}
