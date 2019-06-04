package cn.yami.helgro.db.mongo

import groovy.transform.Canonical

/**
 * 商品定价
 * mins - 2019-6-4 17:11:22
 */
@Canonical
@SuppressWarnings("GroovyUnusedDeclaration")
class ProductPricing {
    // 净价
    Integer wet
    // 零售价
    Integer retail
}
