package com.example.config;


import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName liyufeng
 * @Description TODO
 * @Author Administrator
 * @Date 2019/5/15 14:35
 * @Version 1.0
 **/
@Configuration
public class ProducerConfigure {

    Logger log = LoggerFactory.getLogger(ProducerConfigure.class);

    @Autowired
    private ProducerConfig producerConfigure;

    @Bean
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        log.info(producerConfigure.toString());
        log.info("MailProducer 正在创建---------------------------------------");

        DefaultMQProducer producer=new DefaultMQProducer(producerConfigure.getGroupName());
        producer.setNamesrvAddr(producerConfigure.getNamesrvAddr());
        //rocketmq.remoting.exception.RemotingConnectException: connect to <192.168.237.133:10909> failed  因为rocketmq默认开启了vip通道：10911 - 2 = 10909
        //producer.setVipChannelEnabled(false);
        //设置发送异步失败时的重试时间
       // producer.setRetryTimesWhenSendAsyncFailed(10);
        producer.start();

        log.info("rocketmq MailProducer server开启成功---------------------------------.");
        return producer;
    }
}
