package cn.yami.helgro.db.mongo

import groovy.transform.Canonical

/**
 * 商品细节
 * mins - 2019-6-4 17:13:04
 */
@Canonical
@SuppressWarnings("GroovyUnusedDeclaration")
class ProductDetails {
    String title

    // 音乐专辑类
    // 类型：爵士、摇滚
    String genre
    String artist

    // 电影类
    String director
    String writer
    String actor

}
