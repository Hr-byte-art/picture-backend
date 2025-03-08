package com.wjh.picturebackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author 
 * @Date 2025/2/2 23:24:50
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //覆盖所有请求
        registry.addMapping("/**")
                //允许跨域访问的域名(必须使用pattern，否则*会和allowCredentials冲突)
                // 使用 allowedOriginPatterns 而不是 allowedOrigins
                .allowedOriginPatterns("*")
                // 支持凭据
                .allowCredentials(true)
                // 允许的HTTP方法
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                // 允许的所有请求头
                .allowedHeaders("*")
                // 暴露的响应头
                .exposedHeaders("*");
    }
}