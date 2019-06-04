package cn.yami.helgro.web.controllers

import cn.yami.helgro.db.mongo.Product
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class ProductController {

    @PostMapping("/register")
    def registerProduct(@RequestBody Map<String, String> body) {

        String type = body.get("type")
        String title = body.get("title")
        String description = body.get("description")

        Product product = new Product()
        product.

        if (type == "Audio Album") {
            // 音乐类
            String detailsTitle = body.get("details.title")
            String detailsGenre = body.get("details.genre")
            String detailsArtist = body.get("details.artist")
        } else if (type == "Film") {
            // 电影类
            String detailsDirector = body.get("details.director")
            String detailsWriter = body.get("details.writer")
            String detailsActor = body.get("details.actor")
        }


    }
}
