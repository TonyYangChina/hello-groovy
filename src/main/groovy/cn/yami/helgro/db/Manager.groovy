package cn.yami.helgro.db

import grails.gorm.annotation.Entity
import groovy.transform.ToString

/**
 * mins 2019-6-3 01:22:15
 * 经理类
 */
@Entity
@ToString
class Manager {
    Integer id
    String name
    int status

    static  hasMany = [stores: Store]

}
