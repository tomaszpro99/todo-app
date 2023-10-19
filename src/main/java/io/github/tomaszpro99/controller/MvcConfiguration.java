package io.github.tomaszpro99.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Set;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    private Set<HandlerInterceptor> interceptors; //interceptor'y | classy które mają: implements HandlerInterceptor

    public MvcConfiguration(Set<HandlerInterceptor> interceptor) {
        this.interceptors = interceptor;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
//        registry.addInterceptor(new LoggerInterceptor()); //jeden
        interceptors.stream().forEach(registry::addInterceptor); //wszystkie
    }
}
