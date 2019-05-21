package com.example.mailconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example")
@EnableEurekaClient//FeignClient也要向Eureka注册中心注册，否则报404
@EnableFeignClients(basePackages={"com.example.client"})//由于使用pom引入service jar包，如果不加basePackage，会找不到包所在路径
public class MailConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailConsumerApplication.class, args);
    }

}
