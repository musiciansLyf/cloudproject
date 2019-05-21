package com.example.config;


import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * @ClassName liyufeng
 * @Description TODO
 * @Author Administrator
 * @Date 2019/5/17 10:45
 * @Version 1.0
 **/
@Configuration
public class LogConfig {
    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.HEADERS;
    }
}
