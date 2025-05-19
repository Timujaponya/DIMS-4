package com.sau.bm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // tüm API'lere izin ver
                .allowedOrigins("http://localhost:5173") // sadece React frontend erişsin
                .allowedMethods("*");
    }
}
