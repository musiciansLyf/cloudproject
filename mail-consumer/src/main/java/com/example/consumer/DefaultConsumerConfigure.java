package com.example.consumer;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @ClassName liyufeng
 * @Description TODO
 * @Author Administrator
 * @Date 2019/5/15 11:00
 * @Version 1.0
 **/
@Configuration
public abstract class DefaultConsumerConfigure {
    Logger log= LoggerFactory.getLogger(DefaultConsumerConfigure.class);

    @Autowired
    private ConsumerConfig consumerConfig;
    //开启消费服务
    public void listener(String topic,String tag) throws MQClientException {
        log.info("开启"+topic+"的"+tag+"消费者-------------");
        log.info(consumerConfig.toString()+"———————————"+consumerConfig.getNamesrvAddr());

        DefaultMQPushConsumer consumer=new DefaultMQPushConsumer(consumerConfig.getGroupName());
        consumer.setNamesrvAddr(consumerConfig.getNamesrvAddr());
//        consumer.setVipChannelEnabled(false);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.setConsumeThreadMin(20);
        consumer.setConsumeThreadMax(64);
        consumer.subscribe("mail","text");
        //开启内部类监听
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            log.info("监听类内部");
            return DefaultConsumerConfigure.this.dealBody(msgs);
        });

        consumer.start();

        log.info("rocketmq启动成功---------------------------------------");
    }

    public abstract ConsumeConcurrentlyStatus dealBody(List<MessageExt> msgs);
}
