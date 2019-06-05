package cn.yami.helgro.db.mongo

import groovy.transform.Canonical
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Index

/**
 * 商品
 * 2019-6-4 16:59:32
 */
@Canonical
@SuppressWarnings("GroovyUnusedDeclaration")
class Product {

    // TODO @Index 索引的添加
    // Long code

    @Id
    String id
    String type
    String title
    String description
    // 平台ID
    String asin

    // 上架情况：0-未上架，1-上架
    Integer status

    Map<String, Object> details
    Map<String, Object> pricing
}