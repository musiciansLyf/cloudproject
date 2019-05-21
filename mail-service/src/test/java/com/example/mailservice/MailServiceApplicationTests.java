package com.example.mailservice;

import com.example.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceApplicationTests {
    @Resource
    private MailService mailService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void sendText(){
        mailService.sendSimpleMail("1207703156@qq.com","测试","你好");
    }

    @Test
    public void sendHtml(){
        mailService.sendAttachmentsMail("1207703156@qq.com","测试","<h2>你好<h2>",null,true);
    }

}
