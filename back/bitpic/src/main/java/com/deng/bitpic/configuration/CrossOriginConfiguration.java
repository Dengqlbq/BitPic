package com.deng.bitpic.configuration;

import com.deng.bitpic.interceptor.CrossOriginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: 跨域配置
 * @author: Deng
 * @create: 2019-02-26
 */
@Configuration
public class CrossOriginConfiguration implements WebMvcConfigurer {

    @Bean
    CrossOriginInterceptor crossOriginInterceptor() {
        return new CrossOriginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(crossOriginInterceptor());
    }
}
