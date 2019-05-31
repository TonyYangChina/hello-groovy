package cn.yami.helgro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

import static org.springframework.boot.SpringApplication.run

/**
 *  mins 2019-5-30 10:53:23
 */
@EnableAsync
@EnableScheduling
@SpringBootApplication
class Application extends SpringBootServletInitializer {
    static void main(String[] args) {
        run(Application.class, args)
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.sources(Application.class)
    }
}
