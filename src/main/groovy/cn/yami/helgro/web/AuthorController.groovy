package cn.yami.helgro.web

import cn.yami.helgro.db.Author
import cn.yami.helgro.db.Song
import cn.yami.helgro.service.AuthorService
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletRequest

/**
 *  mins 2019-6-2 15:13:05
 *
 *  author 1:n Song
 */
@Slf4j
@SuppressWarnings("GroovyUnusedDeclaration")
@RestController
@RequestMapping("/author")
class AuthorController {

    @Autowired
    private AuthorService authorService

    @Transactional
    @PostMapping(value = "/add", produces = "application/json")
    def add(HttpServletRequest request,
            // 关于配置value和required是因为前端报出Required String parameter 'xxxx' is not present
            @RequestParam(value="name") String name,
            @RequestParam(value="nickName",required = false) String nickName,
            @RequestParam(value="songTitles") String songTitles,
            Model model) {
        def author = new Author(name: name, nickName: nickName)
        String[] titles = songTitles.split(",")
        for (String title : titles) {
            author.addToSongs(new Song(title: title))
        }

        // grail建议不使用立刻刷新
        if (!author.save(flush:true, failOnError:true)) {
            author.errors.each {
                log.error(it as String)
            }
        }

        return [ret: 0, msg: "OK", data: '']

    }

    @Transactional
    @PostMapping(value = "/get", produces = "application/json")
    def get(HttpServletRequest request,
            @RequestParam(value="name",required = false) String name,
            @RequestParam(value="id",required = false) Integer id,
            @RequestParam(value="nickName",required = false) String nickName,
            @RequestParam(value="songTitle",required = false) String songTitle,
            Model model) {

        Author author = new Author(name: name, id: id, nickName: nickName)
        // 报事务异常
        // Author result = authorService.findAuthorByParam(author)
        Author result
        if (null != author.id) {
            result =  Author.findByIdAndStatus(author.id, 0)
        } else {
            if (StringUtils.isNotBlank(author.name) || StringUtils.isNotBlank(author.nickName)) {
                if (StringUtils.isNotBlank(author.name) && StringUtils.isNotBlank(author.nickName)) {
                    result = Author.findByNameAndNickName(author.name, author.nickName)
                } else if (StringUtils.isNotBlank(author.name)) {
                    result = Author.findByName(author.name)
                } else {
                    result = Author.findByNickName(author.nickName)
                }
            }
        }
        if (null != result) {
            return [ret: 0, msg: "OK", data: [result.id, result.name, result.nickName]]
        }
        return [ret: 1, msg: "无数据"]

    }

    @Transactional
    @PostMapping(value = "/list", produces = "application/json")
    def list(HttpServletRequest request,
            @RequestParam(value="author_name",required = false) String name,
            @RequestParam(value="author_nickName",required = false) String nickName,
            Model model) {


        List<Author> authors = Author.withCriteria {
            eq('status', 0)
            if (null != name) {
                like('name', "%"+name+"%")
            }
            if (null != nickName) {
                like('nickName', "%"+nickName+"%")
            }
        } as List<Author>

        return [ret: 0, msg: "OK", data: authors.collect{[name : it.name]}]

    }

}
