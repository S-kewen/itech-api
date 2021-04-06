package com.boot.yuntechlife.crossOrigin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: skwen
 * @Description: MyConfiguration-跨域訪問配置
 * ClassName: MyConfiguration
 * @Date: 2020-03-08
 */
@Configuration
public class MyConfiguration {
    @Value("${crossOrigin.host}")
    private String crossOrigin;

    @Bean
    @Order(1)
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override

            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowCredentials(true)
                        .maxAge(3600)
                        .allowedMethods("POST");
            }

        };

    }
}
