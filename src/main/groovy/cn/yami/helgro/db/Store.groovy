package cn.yami.helgro.db

import grails.gorm.annotation.Entity
import groovy.transform.ToString

/**
 * mins 2019-6-3 01:23:18
 * 门店类
 *
 * 与经理类 N:N 关系
 * 产生中间表，manager_stores
 * Manager负责维护关系
 */
@Entity
@ToString
@SuppressWarnings("GroovyUnusedDeclaration")
class Store {
    static belongsTo = Manager
    static hasMany = [managers: Manager]
    String name
}
