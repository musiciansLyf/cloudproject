package com.example.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.MyFile;
import com.example.mapper.FileMapper;
import com.example.service.MailService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
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
public class MailServiceImpl extends ServiceImpl<FileMapper, MyFile> implements MailService {

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
    public boolean sendAttachmentsMail(String sendTo, String titel, String content, List<Pair<String, File>> attachments, Boolean isHtml) {
      /*  MimeMessage mimeMailMessage=javaMailSender.createMimeMessage();
        try {
            System.out.println(mimeMailMessage);
            //true表示需要创建一个multipart message
            MimeMessageHelper helper=new MimeMessageHelper(mimeMailMessage,isHtml);
            helper.setFrom(from);
            helper.setTo(sendTo);
            helper.setSubject(titel);
            helper.setText(content,isHtml);
            for (Pair<String, File> pair : attachments) {
                helper.addAttachment(pair.getKey(), new FileSystemResource(pair.getValue()));
            }
            javaMailSender.send(mimeMailMessage);
            System.out.println("发送成功");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("发送失败");
            return false;
        }*/
      return true;
    }

    @Override
    public void sendTemplateMail(String sendTo, String titel, Map<String, Object> content, List<Pair<String, MyFile>> attachments) {

    }
}
