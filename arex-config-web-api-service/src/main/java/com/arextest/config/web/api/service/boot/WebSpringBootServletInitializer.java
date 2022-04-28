package com.arextest.config.web.api.service.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


/**
 * SpringBoot web Application Servlet Initializer
 *
 * @author jmo
 * @since 2021/8/18
 */
@SpringBootApplication(scanBasePackages = "com.arextest.config")
@MapperScan("com.arextest.config.core.repository.mapper")
public class WebSpringBootServletInitializer extends SpringBootServletInitializer {

    /**
     * configure for our Servlet
     *
     * @param application builder
     * @return build a source
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebSpringBootServletInitializer.class);
    }
}
