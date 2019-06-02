package cn.yami.helgro.db

import grails.gorm.annotation.Entity
import groovy.transform.ToString

/**
 * 创作者创作的歌曲
 */
@Entity
@ToString
@SuppressWarnings("GroovyUnusedDeclaration")
class Song {
    // 不设置id，grail默认在表中生成id字段
    Integer id
    String title

    /**
     *  添加belongsTo，使得Author的hasMany的方式，
     *      从单向的一对多（grail默认生成一张中间表的形式：author_song ： author_song_id & song_id字段）
     *          默认级联更新和保存，但是不会级联删除
     *      变成双向的一对多（表结构也发生变化，不会有中间表，外键字段被加在多的一端）
     *          默认级联更新和保存以及级联删除
     */
    static belongsTo = [author: Author]
}
