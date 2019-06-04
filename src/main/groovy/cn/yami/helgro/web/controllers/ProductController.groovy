package cn.yami.helgro.web.controllers

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



    }
}
