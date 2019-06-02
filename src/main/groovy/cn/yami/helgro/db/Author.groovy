package cn.yami.helgro.db

import grails.gorm.annotation.Entity
import groovy.transform.ToString

/**
 * 创作者
 * mins 2019-6-2 15:04:02
 */
@Entity
@ToString
@SuppressWarnings("GroovyUnusedDeclaration")
class Author implements Serializable {
    Integer id
    String name
    int status
    String nickName

    static hasMany = [songs : Song]

    @SuppressWarnings("GrUnresolvedAccess")
    static mapping = {
        status defaultValue: 0
    }
}
