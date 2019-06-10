package cn.yami.helgro.web

import cn.yami.helgro.common.annotation.Log
import groovy.util.logging.Slf4j
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Groovy 问候
 */
@Slf4j
@RestController
@RequestMapping("/hello")
class SayHelloController {

    @Transactional
    @Log("Hell的say方法")
    @RequestMapping("/say")
    def say() {
        return "hello groovy"
    }


    /**
     * 测试groovy方法定义与Java的差异
     * 1.参数无类型
     * 2.println无括号
     * 3.
     * @param args
     */
    static void main(args) {
        System.out.println("hello groovy")
        // 双引号中，groovy会将$识别成表达式
        // System.out.println("hello $groovy")
        System.out.println('hello $groovy')
        println "hello groovy"
        // println "hello $groovy"
        println 'hello $groovy'

        type()

        loop()

    }

    /**
     * 数据类型
     *
     * def —— 灵活性 — 不需要接口或抽象类？
     */
    static void type() {
        // = 号右侧的字符已经表明数据类型是字符串了
        // groovy 根据值判断数据类型
        def value = "hello groovy 2019-5-30 11:25:05"
        println value
        println value.class

        def intVal = 12
        println intVal
        println intVal.class
        int intVal2 = 12
        // 还是打印java.lang.Integer - 万物皆对象
        println intVal2.class

        println '==========循环、重复操作========='
        def arr = ['hapi', 'lulu', 'lili']
        loop(arr)
        // 将字符串作为参数，默认会进行拆分
        loop('Hello groovy')

        // 重复打印，非循环
        repeat('put your hands up!')
        // 指定重复次数
        repeat('yeah!', 3)

        println '==========集合操作========='
        // 集合操作
        collect()

        println '==========映射操作========='
        map()

        println '==========闭包=========='
        closure()
    }

    /**
     * 遍历值，循环
     */
    static void loop(val) {

        for (i in val) {
            println i
        }
    }


    /**
     * 对一个值进行重复打印
     * @param val
     * @param repeat 默认参数值
     */
    static void repeat(val, repeat=5) {

        for (i in 0..<repeat) {
            println val
        }
    }

    /**
     * 集合
     *
     * 疑问，rang是List，但不是ArrayList
     */
    static void collect() {
        def range = 0..4
        println range.class
        assert range instanceof List
        assert range instanceof Collection

        def arr = ['hapi', 'lulu', 'lili']
        println arr.class
        assert arr instanceof List
        assert arr instanceof ArrayList
        assert arr instanceof Collection

        arr.add('qiqi')
        arr << 'didi'
        arr[5] = 'aiai'


        def numbers = [1,2,3,4]
        assert numbers + 5 == [1,2,3,4,5]
        println numbers + 5
        assert numbers - [2,3] == [1,4]
        println numbers - [2,3]

        // 魔术方法
        def numbers2 = [1,2,3,4]
        // 将数据的元素通过"," 进行拼接
        assert numbers2.join(",") == "1,2,3,4"
        // 统计 3 的个数
        assert [1,2,3,4,3].count(3) == 2

        assert ["JAVA", "GROOVY"] ==
                ["Java", "Groovy"]*.toUpperCase()

    }

    /**
     * 映射
     */
    static void map() {

        def hash = [id: "T15301", name: "yami"]
        println hash.get("id")
        println hash.name
        println hash.getClass()

        hash.put("age", 23)
        println hash.age.class
        hash.address = '金山大桥'
        println hash.address
        println hash["address"]
    }

    /**
     * 闭包
     */
    static void closure() {
        def acoll = ["hapi", 'lulu', 'lili']
        /*for (Iterator iter = acoll.iterator(); iter.hasNext();) {
            println iter.next()
        }*/
        // 升级，使用闭包
        // 由 {} 包围起来的代码块就是闭包，即可执行的代码块
        // each 直接在 acoll 实例内调用, acoll是ArrayList
        println acoll.class
        acoll.each {println it}
        // 使用val代替it
        acoll.each {val -> println val }

        def hash = [id: 'T15301', name: 'yami']
        // 多个参数
        hash.each{key,val ->
            // 单引号是java字符串，不识别$
            println "${key}-${val}"
        }

        "hello groovy".each{
            print it.toUpperCase()}

        println ""
        def excite = { word ->
            return "${word}!!"
        }
        // 延迟执行
        println excite("jfdf")


    }
}
