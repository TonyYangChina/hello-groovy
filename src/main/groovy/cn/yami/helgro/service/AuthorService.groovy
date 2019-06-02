package cn.yami.helgro.service

import cn.yami.helgro.db.Author
import cn.yami.helgro.db.User


/**
 *  mins 2019-6-2 23:51:01
 */
interface AuthorService {

    /*根据token查找用户*/
    Author findAuthorByParam(Author author)

}