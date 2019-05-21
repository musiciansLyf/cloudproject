package com.example.consumer;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.example.client.FileClient;
import com.example.entity.MyFile;
import com.example.service.MailService;

import feign.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.mail.util.ByteArrayDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName liyufeng
 * @Description TODO
 * @Author Administrator
 * @Date 2019/5/15 11:13
 * @Version 1.0
 **/
@Configuration
public class MailCustomConsumer extends DefaultConsumerConfigure implements ApplicationListener<ContextRefreshedEvent> {

    Logger log= LoggerFactory.getLogger(MailCustomConsumer.class);

    @Autowired
    private MailService mailService;

    @Autowired
    private FileClient fileClient;

    @Override
    public ConsumeConcurrentlyStatus dealBody(List<MessageExt> msgs) {
        int num=1;
        log.info("进入处理");
        for (MessageExt msg: msgs) {
            log.info("第"+(num++)+"次消息");
            try {
                String msgStr=new String(msg.getBody(),"utf-8");
                log.info(msgStr);
                JSONObject json= JSON.parseObject(msgStr);
                String sendTo=json.getString("sendTo");
                String titel=json.getString("titel");
                String content=json.getString("content");
                Boolean isHtml=json.getBoolean("isHtml");
                log.info("消费者接发送到"+sendTo);
                List<MyFile> files=null;
                List<String> filenames=null;
                List<ByteArrayDataSource> fileByteArrays=null;
                JSONArray array=json.getJSONArray("file");
                if(array!=null && array.size()>0){
                    filenames=new ArrayList<>();
                    fileByteArrays=new ArrayList<>();
                    log.info("判断"+array.size());
                    files=new ArrayList<>();
                    for (int i=0;i<array.size();i++){
                        files.add(array.getObject(i,MyFile.class));
                        log.info(files.get(i).getFileId());
                        Response fileBody=fileClient.download(files.get(i).getFileId());
                        InputStream inputStream=null;
                        byte[] body=null;
                        try {
                            inputStream=fileBody.body().asInputStream();
                            body=new byte[inputStream.available()];
                            int read = inputStream.read(body);
                            log.info("下载文件大小"+read);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ByteArrayDataSource byteArrayDataSource=new ByteArrayDataSource(body,files.get(i).getFileContentType());
                        fileByteArrays.add(byteArrayDataSource);
                        filenames.add(files.get(i).getFileOriginate());
                    }
                }
                return mailService.sendAttachmentsMail(sendTo,titel,content,fileByteArrays,filenames,isHtml);
            } catch (UnsupportedEncodingException e) {
                log.error("body转字符串解析失败");
                e.printStackTrace();
            }
        }
        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("进入处理-----"+contextRefreshedEvent.getApplicationContext().getParent().getParent());//防止onApplicationEvent()方法执行两次
        if(contextRefreshedEvent.getApplicationContext().getParent().getParent() == null){
            try {
                System.out.println("监听器启动中");
                super.listener("mail","text");
            } catch (MQClientException e) {
                log.error("消费者监听器启动失败", e);
                e.printStackTrace();
            }
        }

    }


}
