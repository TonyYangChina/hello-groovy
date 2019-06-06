package cn.yami.helgro.web.controllers

import cn.yami.helgro.db.mongo.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import java.util.regex.Pattern

import static org.springframework.data.mongodb.core.query.Criteria.where
import static org.springframework.data.mongodb.core.query.Query.query
import static org.springframework.data.mongodb.core.query.Query.query

@RestController
@RequestMapping("/product")
class ProductController {

    @Autowired
    private MongoTemplate mongoTemplate

    @RequestMapping(value = "/register", produces = "application/json")
    def registerProduct(@RequestBody Map<String, String> body) {

        String type = body.get("type")
        String title = body.get("title")
        String description = body.get("description")

        Product product = new Product()
        product.type = type
        product.title = title
        product.description = description


        Map<String, Object> details = new HashMap<String, Object>()
        product.details = details
        if (type == "Audio Album") {
            // 音乐类
            // 详细标题
            String detailsTitle = body.get("details.title")
            // 风格
            String detailsGenre = body.get("details.genre")
            // 创作者
            String detailsArtist = body.get("details.artist")
            details.title = detailsTitle
            details.genre = detailsGenre
            details.artist = detailsArtist

        } else if (type == "Film") {
            // 电影类
            // 导演
            String detailsDirector = body.get("details.director")
            // 剧本创作者
            String detailsWriter = body.get("details.writer")
            // 演员,暂时一个
            String detailsActor = body.get("details.actor")

            details.director = detailsDirector
            details.writer = detailsWriter
            details.actor = detailsActor
        }

        // save 插入保存，主键存在，更新数据
        // insert 若新增数据的主键已经存在，则会抛 org.springframework.dao.DuplicateKeyException 异常提示主键重复，不保存当前数据
        // 对主键的
        // mongoTemplate.insert(product, "product")
        // collectName可以不指定，默认获取实体名
        mongoTemplate.insert(product)
        // mongoTemplate.save(product, "product")

        // TODO 对简单的数据操作，对复杂的内嵌子对象，是否有不良反应
        // mongoTemplate.upsert(product)

        return [ret: 0, msg: "录入成功"]
    }

    /**
     * 批量导入民谣歌曲
     * @return
     */
    @RequestMapping(value = "/batchImpFolk", produces = "application/json")
    def batchImpFolk() {

        // 音乐1
        Product product1 = new Product()
        product1.type = "Audio Album"
        product1.title = "这一生关于你的风景"
        product1.description = "来自专辑《这一生关于你的风景》 作者枯木逢春"
        Map<String, Object> details = new HashMap<String, Object>()
        product1.details = details
        details.title = "这一生关于你的风景 枯木逢春"
        details.genre = "Folk"
        details.artist = "枯木逢春"

        // 音乐2
        Product product2 = new Product()
        product2.type = "Audio Album"
        product2.title = "理想国"
        product2.description = "来自专辑《理想国》 作者鲤鱼滑梯"
        details = new HashMap<String, Object>()
        product2.details = details
        details.title = "理想国 枯木逢春"
        details.genre = "Folk"
        details.artist = "鲤鱼滑梯"

        // 音乐3
        Product product3 = new Product()
        product3.type = "Audio Album"
        product3.title = "四块五"
        product3.description = "来自专辑《四块五》 作者隔壁老樊"
        details = new HashMap<String, Object>()
        product3.details = details
        details.title = "四块五 隔壁老樊"
        details.genre = "Folk"
        details.artist = "隔壁老樊"

        List<Product> products = new ArrayList<>()
        products.add(product1)
        products.add(product2)
        products.add(product3)

        // 批处理
        // insert: 可以一次性插入一整个列表，而不用进行遍历操作，效率相对较高
        // save: 需要遍历列表，进行一个个的插入
        mongoTemplate.insert(products, "product")



        return [ret: 0, msg: "录入成功"]
    }

    /**
     * 更新电影数据
     * @param body
     * @return
     */
    @PostMapping("/modifyFilm")
    def modifyFilm(@RequestBody Map<String, String> body) {
        String id = body.get("id")
        String detailsWriter = body.get("details.writer")
        Double retail = body.get("pricing.retail").toDouble()

        /*Product product = mongoTemplate.findOne(Query.query(where("id").is(id)), Product.class)

        // 剧本创作者
        product.details.writer = detailsWriter

        // 对Product进行属性的扩展，添加 pricing
        if (product.pricing == null) {
            product.pricing = new HashMap<>()
        }
        product.pricing.retail = retail
        mongoTemplate.save(product, "product")*/


        // 使用 findAndModify 替换以上代码
        /*Query query = new Query()
        query.addCriteria(Criteria.where("id").is(id))
        Update update = new Update()
        update.set("detail.writer", detailsWriter)

        // 对于之前不存在的属性map，自动新增，添加到mongo中
        update.set("pricing.retail", retail)

        mongoTemplate.findAndModify(query, update, Product.class)*/

        Query query = new Query()
        if (id) {
            query.addCriteria(Criteria.where("id").is(id))
        }
        Update update = new Update()
        if (detailsWriter) {
            update.set("details.writer", detailsWriter)
        }


        // update+insert 如果根据条件没有对应的数据,则执行插入
        mongoTemplate.upsert(query, update.set("pricing.retail", retail),
                Product.class)

        return [ret: 0, msg: "修改成功"]
    }

    /**
     * 更新音乐数据
     * @param body
     * @return
     */
    @PostMapping("/modifyAudio")
    def modifyAudio(@RequestBody Map<String, String> body) {
        String id = body.get("id")
        Double retail = body.get("pricing.retail").toDouble()

        // update+insert 如果根据条件没有对应的数据,则执行插入
        mongoTemplate.upsert(query(where("id").is(id)),
                new Update().set("pricing.retail", retail), Product.class)

        return [ret: 0, msg: "修改成功"]
    }

    /**
     * 更新数据
     * @param body
     * @return
     */
    @PostMapping("/remove")
    def remove(@RequestBody Map<String, String> body) {
        String id = body.get("id")
        Product product = mongoTemplate.findOne(Query.query(where("id").is(id)), Product.class)
        mongoTemplate.remove(product, "product")

        return [ret: 0, msg: "删除成功"]
    }

    /**
     * 查询列表
     * @param type
     * @param title
     * @param description
     * @return
     */
    @RequestMapping(value = "/find", produces = "application/json")
    @SuppressWarnings(["GroovyAssignabilityCheck"])
    find(@RequestParam(required = true) String type,
         // @RequestParam(required = false) Integer status,
         @RequestParam(required = false) String title,
         @RequestParam(required = false) String description) {


        Query query = new Query()
        // 一次定义，后期接着使用
        Criteria criteria = Criteria.where("type").is(type)

        query.addCriteria(criteria)
        /*if (status) {
            criteria.andOperator(Criteria.where("status").is(status))
        }*/

        // 模糊查询
        if (title) {
            Pattern pattern = Pattern.compile('^.*' + title + '.*$', Pattern.CASE_INSENSITIVE)
            criteria.orOperator(Criteria.where("title").regex(pattern))
        }
        if (description) {
            Pattern pattern = Pattern.compile('^.*' + description + '.*$', Pattern.CASE_INSENSITIVE)
            criteria.orOperator(Criteria.where("description").regex(pattern))
        }

        // 排序
        // Sort sort = new Sort(Sort.Direction.DESC, "title")
        // 使用details内部的属性排序
        Sort sort = new Sort(Sort.Direction.DESC, "details.genre")
        query.with(sort)

        // TODO groovy对数据的选择性输出到前端
        // 列表
        // TODO find的参数Map.class的作用
        List<Product> products = mongoTemplate.find(query,'product')
        // 数量统计
        def productsCount = mongoTemplate.count(query, 'product')

        return [ret: 0, msg: "OK", data: products]
    }
}
