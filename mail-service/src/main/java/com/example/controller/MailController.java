package com.example.controller;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendCallback;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import feign.Param;
import net.sf.json.JSONObject;
import com.example.entity.MyFile;
import com.example.service.FileService;
import com.example.service.MailService;
import javafx.util.Pair;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName liyufeng
 * @Description TODO
 * @Author Administrator
 * @Date 2019/5/13 20:28
 * @Version 1.0
 **/
@RestController
@RequestMapping("/mail")
public class MailController {

    Logger log= LoggerFactory.getLogger(MailController.class);


    @Autowired
    private DefaultMQProducer producer;

    /*@PostMapping("/mailservice")
    public String sendMail(@RequestParam String sendTo,@RequestParam String titel,@RequestParam String content,@RequestParam(name = "file",required = false) MultipartFile file,@RequestParam Boolean isHtml){
        String upload="D:\\projectTeam\\team\\project\\cloudproject\\mail-service\\src\\main\\resources\\upload\\";
        List<MultipartFile> files=new ArrayList<>();
        System.out.println("file：   "+file);
        if(file!=null){
            files.add(file);
        }
        List<Pair<String, File>> pairList=new ArrayList<>();
        Pair<String, File> pair;
        MyFile myfile;
        String fileType;
        String originalFilename;
        for (int i=0;i<files.size();i++){
            originalFilename=files.get(i).getOriginalFilename();
            System.out.println(files.size());
            fileType=originalFilename.substring(originalFilename.lastIndexOf("."));
            myfile=new MyFile();
            myfile.setFileName(RandomStringUtils.randomAlphanumeric(5)+fileType);
            myfile.setFileOriginate(files.get(i).getOriginalFilename());
            myfile.setFilePath(upload+myfile.getFileName());
            try {
                files.get(i).transferTo(new File(myfile.getFilePath()));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("文件转换失败");
            }
            if(fileService.save(myfile)) {
                pair = new Pair<>(myfile.getFileName(), new File(myfile.getFilePath()));
                System.out.println(pair);
                pairList.add(pair);
            }else {
                return "数据保存文件失败";
            }
        }
        if(mailService.sendAttachmentsMail(sendTo,titel,content,pairList,isHtml)){
            return "发送成功";
        }else {
            return "发送失败";
        }
    }*/

    @PostMapping("/mailservice")
    public String sendMailTest(@RequestParam("sendTo") String sendTo, @RequestParam("titel") String titel,
                               @RequestParam("content") String content, @RequestPart("file") List<MyFile> file,
                               @RequestParam("isHtml") Boolean isHtml) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        System.out.println(sendTo+titel+content+isHtml+"     "+file.size());
        Map map = new HashMap();
        map.put("sendTo",sendTo);
        map.put("titel", titel);
        map.put("content", content);
        map.put("isHtml", isHtml);
        if (file.size()!=0){
            log.info("接受到"+file.get(0).getFileOriginate());
            map.put("file",file);
        }
        JSONObject json = JSONObject.fromObject(map);

        Message message=new Message("mail","text","123",json.toString().getBytes()){};
        //异步处理，可以得到发送到mq的情况，并做相应处理
        producer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("传输成功");
                log.info(sendResult.toString());
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("传输失败",throwable);
            }
        });
        return "发送成功";
    }
}
