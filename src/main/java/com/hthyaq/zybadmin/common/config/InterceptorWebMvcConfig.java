package com.hthyaq.zybadmin.common.config;

import com.hthyaq.zybadmin.common.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorWebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
                //去除拦截主页，登录页面，注册页面，忘记密码页面,静态资源
                .excludePathPatterns("/user/login", "/user/register", "/forgotPassword", "/static/**");
    }
}
