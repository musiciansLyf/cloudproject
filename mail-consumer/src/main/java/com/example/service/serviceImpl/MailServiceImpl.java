package com.example.service.serviceImpl;


import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.example.service.MailService;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @ClassName liyufeng
 * @Description TODO
 * @Author Administrator
 * @Date 2019/5/13 13:52
 * @Version 1.0
 **/
@Service
public class MailServiceImpl implements MailService {

    Logger log=LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;

    @Override
    public void sendSimpleMail(String sendTo, String titel, String content) {
/*        System.out.println(from);
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(sendTo);
        simpleMailMessage.setSubject(titel);
        simpleMailMessage.setText(content);
        try {
            javaMailSender.send(simpleMailMessage);
            System.out.println("发送成功");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("发送失败");
        }*/
    }

    @Override
    public ConsumeConcurrentlyStatus sendAttachmentsMail(String sendTo, String titel, String content, List<ByteArrayDataSource> fileBytes,
                                                         List<String> filenames, Boolean isHtml) {
        MimeMessage mimeMailMessage=javaMailSender.createMimeMessage();
        try {
            log.info("发送到"+sendTo);
            //true表示需要创建一个multipart message
            MimeMessageHelper helper=new MimeMessageHelper(mimeMailMessage,isHtml);
            helper.setFrom(from);
            helper.setTo(sendTo);
            helper.setSubject(titel);
            helper.setText(content,isHtml);
            if(fileBytes!=null&&fileBytes.size()>0){
                for (int i=0;i<fileBytes.size();i++){
                    helper.addAttachment(filenames.get(i), fileBytes.get(i));
                }
            }
/*            for (Pair<String, File> pair : attachments) {
                helper.addAttachment(pair.getKey(), new FileSystemResource(pair.getValue()));
            }*/
            javaMailSender.send(mimeMailMessage);
            log.info("邮件发送成功");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        } catch (MessagingException e) {
            log.info("邮件发送失败");
            e.printStackTrace();
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }

    @Override
    public void sendTemplateMail(String sendTo, String titel, Map<String, Object> content, List<Pair<String, File>> attachments) {

    }


}
