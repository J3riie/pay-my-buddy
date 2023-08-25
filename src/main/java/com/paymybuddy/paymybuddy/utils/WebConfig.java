package com.paymybuddy.paymybuddy.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**", "/images/**", "/css/**").addResourceLocations("classpath:/META-INF/resources/webjars/",
                "classpath:/static/images/", "classpath:/static/css/");
    }
}
