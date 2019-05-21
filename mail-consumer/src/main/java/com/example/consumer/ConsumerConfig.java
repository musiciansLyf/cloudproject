package com.example.consumer;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName liyufeng
 * @Description TODO
 * @Author Administrator
 * @Date 2019/5/15 10:45
 * @Version 1.0
 **/
@Component
@ConfigurationProperties(prefix = "rocketmq.consumer")
@Configuration
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ConsumerConfig {

    private String namesrvAddr;

    private String groupName;
}
