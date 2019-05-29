package cn.yami

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

import static org.springframework.boot.SpringApplication.run

/**
 *  fanwu 编写于 2017/3/3.
 */
@EnableAsync
@EnableScheduling
@SpringBootApplication
// @ComponentScan(basePackages = ["com.ktvme.mgrapp", "com.km"])
class Application extends SpringBootServletInitializer {
    static void main(String[] args) {
        run(Application.class, args)
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.sources(Application.class)
    }
}
