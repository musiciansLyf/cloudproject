package com.example.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName liyufeng
 * @Description TODO
 * @Author Administrator
 * @Date 2019/5/15 14:31
 * @Version 1.0
 **/
@Component
@ConfigurationProperties(prefix = "rocketmq.producer")
@Configuration
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProducerConfig {

    private String namesrvAddr;

    private String groupName;
}
