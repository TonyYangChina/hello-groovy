package cn.yami.helgro.db.mongo

import groovy.transform.Canonical
import org.springframework.data.annotation.Id

/**
 * 商品
 * 2019-6-4 16:59:32
 */
@Canonical
@SuppressWarnings("GroovyUnusedDeclaration")
class Product {
    // 库存量单位：如：SPU + 颜色 + 尺码，就是一个SKU
    @Id
    String sku
    String type
    String title
    String description
    // 平台ID
    String asin

    ProductDetails details
    ProductPricing pricing
}