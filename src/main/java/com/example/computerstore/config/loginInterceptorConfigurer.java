package com.example.computerstore.config;

import com.example.computerstore.interceptor.loginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

//加载当前的拦截器并进行注册
@Configuration
public class loginInterceptorConfigurer implements WebMvcConfigurer {
    //配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //创建自定义的拦截器对象
        HandlerInterceptor interceptor = new loginInterceptor();
        //配置白名单：存放在list里面
        List<String> patterns = new ArrayList<>();
        //static下的样式要放行
        patterns.add("/bootstrap3/**");
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        //web的部分要放行
        patterns.add ("/web/register.html");
        patterns.add ("/web/login.html");
        patterns.add ("/web/index.html");
        patterns.add("/web/ product.html");
        patterns.add("/users/reg");
        patterns.add("/users/login");
        patterns.add("/districts/**");
        patterns.add("/products/**");



        registry.addInterceptor(interceptor)
                //黑名单
                .addPathPatterns("/**")
                //白名单
                .excludePathPatterns(patterns);

    }
}
