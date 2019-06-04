package cn.yami.helgro.db

import grails.gorm.annotation.Entity

/**
 * mins 2019-6-3 11:27:36
 */
@Entity
@SuppressWarnings("GroovyUnusedDeclaration")
class Nose {
    static belongsTo = [face: Face]
}
