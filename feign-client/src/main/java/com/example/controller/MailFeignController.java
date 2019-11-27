package com.example.controller;

import com.example.clients.FileClient;
import com.example.clients.MailFeignClient;
import com.example.entity.MyFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName liyufeng
 * @Description TODO
 * @Author Administrator
 * @Date 2019/5/14 10:48
 * @Version 1.0
 **/
@RestController
@RequestMapping("")
public class MailFeignController {

    @Autowired
    private MailFeignClient mailFeignClient;

    @Autowired
    private FileClient fileClient;

    //ConcurrentHashMap concurrentHashMap;

    @RequestMapping(value="/mail", method= RequestMethod.POST)
    public String sendMail(String sendTo,String titel, String content,@RequestParam("file")MultipartFile[] file, Boolean isHtml){
        List<MyFile> myFiles=null;
        MyFile[] files=null;
        if (file!=null){
            System.out.println(file[0].getOriginalFilename());
            myFiles=fileClient.upfile(file);
            //文件上传至fileService出现乱码，这里对文件名进行重新设置
            for (int i=0;i<myFiles.size();i++){
                myFiles.get(i).setFileOriginate(file[i].getOriginalFilename());
            }
            System.out.println(myFiles.get(0).getFileOriginate());
            files=myFiles.toArray(new MyFile[myFiles.size()]);
        }
        return mailFeignClient.sendMail(sendTo,titel,content,files,isHtml);
    }

    @RequestMapping(value="/upfile", method= RequestMethod.POST)
    public List<MyFile> upfile(@RequestParam("file") MultipartFile[] files){
        System.out.println(files.length);
        return fileClient.upfile(files);
    }

}
