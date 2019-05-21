package com.example.config;

import feign.Contract;
import feign.codec.Encoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @ClassName liyufeng
 * @Description TODO
 * @Author Administrator
 * @Date 2019/5/16 14:52
 * @Version 1.0
 **/
@Configuration
public class FeignConfiguration {

    //@RequestMapping是Spring里的注解
    //启动feign自定义注解 如@@RequestLine 使用feign自带的编码器，但是它不支持多文件，需重写
    @Bean
    public Contract feignContract(){
        return new Contract.Default();
    }

    //fei实现多pojo传输与Multiparfile上传的编码器需要配合fei自带的注解使用
    @Bean
    public Encoder feignSpringFormEncoder(){
        return new FeignSpringFormEncoder();
    }
}
