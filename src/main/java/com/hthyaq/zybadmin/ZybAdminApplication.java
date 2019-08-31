package com.hthyaq.zybadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.session.web.http.DefaultCookieSerializer;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@MapperScan("com.hthyaq.zybadmin.mapper")
public class ZybAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZybAdminApplication.class, args);
    }

    //解决页面和redis的sessionId不一致情况
    @Bean
    public DefaultCookieSerializer getDefaultCookieSerializer() {
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
        defaultCookieSerializer.setUseBase64Encoding(false);
        return defaultCookieSerializer;
    }
}
