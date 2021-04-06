package com.boot.yuntechlife.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.MultipartConfigElement;

@Configuration
public class UploadConfig {
    @Value("${upload.config.maxFileSize}")
    private String MaxFileSize;
    @Value("${upload.config.maxRequestSize}")
    private String MaxRequestSize;

    @Bean
    public MultipartConfigElement multipartConfig() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        factory.setMaxFileSize(DataSize.parse(MaxFileSize)); // KB,MB
        /// 总上传数据大小
        factory.setMaxRequestSize(DataSize.parse(MaxRequestSize));
        return factory.createMultipartConfig();
    }

}
