package cn.yami.helgro.service.impl

import cn.yami.helgro.db.Author
import cn.yami.helgro.service.AuthorService
import grails.transaction.Transactional
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Component

/**
 * 范武(fanwu0618@gmail.com) 编写于 2017/3/3.
 */
@SuppressWarnings("GroovyUnusedDeclaration")
@Component
@Slf4j
class AuthorServiceImpl implements AuthorService {

    @Transactional
    @Override
    Author findAuthorByParam(Author author) {
        if (null != author.id) {
             return  Author.findByIdAndStatus(author.id, 0)
        } else {
            if (StringUtils.isNotBlank(author.name) || StringUtils.isNotBlank(author.nickName)) {
                if (StringUtils.isNotBlank(author.name) && StringUtils.isNotBlank(author.nickName)) {
                    Author.findByNameAndNickName(author.name, author.nickName)
                } else if (StringUtils.isNotBlank(author.name)) {
                    Author.findByName(author.name)
                } else {
                    Author.findByNickName(author.nickName)
                }
            } else {
                return null
            }
        }
    }
}
