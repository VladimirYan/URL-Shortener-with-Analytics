package com.example.config;

import com.example.controller.ApiErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebErrorConfig {

    @Bean
    public ApiErrorController apiErrorController(ErrorAttributes errorAttributes) {
        return new ApiErrorController(errorAttributes);
    }
}
