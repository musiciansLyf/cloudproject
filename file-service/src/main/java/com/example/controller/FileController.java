package com.example.controller;


import com.example.entity.MyFile;
import com.example.service.FileService;
import javafx.util.Pair;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @ClassName liyufeng
 * @Description TODO
 * @Author Administrator
 * @Date 2019/5/13 20:28
 * @Version 1.0
 **/
@RestController
@RequestMapping("/file")
public class FileController {

    Logger log= LoggerFactory.getLogger(FileController.class);


    @Autowired
    private FileService fileService;


    @PostMapping("")
    public List<MyFile> upFile(@RequestPart(name = "file") MultipartFile[] files){
        List<MyFile> list=new ArrayList<>();
        String upload="D:\\projectTeam\\team\\project\\cloudproject\\mail-service\\src\\main\\resources\\upload\\";
        log.info("文件名称："+files[0].getOriginalFilename());
        MyFile myfile;
        String fileType;
        String originalFilename;
        for (int i=0;i<files.length;i++){
            originalFilename=files[i].getOriginalFilename();
            fileType=originalFilename.substring(originalFilename.lastIndexOf("."));
            myfile=new MyFile();
            log.info("文件大小："+files[i].getSize());
            myfile.setFileId("MF-"+UUID.randomUUID());
            myfile.setFileName(RandomStringUtils.randomAlphanumeric(5)+fileType);
            myfile.setFileOriginate(files[i].getOriginalFilename());
            myfile.setFilePath(upload+myfile.getFileName());
            myfile.setFileContentType(files[i].getContentType());
            try {
                files[i].transferTo(new File(myfile.getFilePath()));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("文件转换失败");
            }
            if(fileService.save(myfile)) {
                list.add(myfile);
            }else {
                return null;
            }
        }
        return list;
    }

    @PostMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam(name = "fileId")String fileId){


        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<byte[]> entity = null;
        InputStream in=null;
        try {
            String filePath=fileService.getById(fileId).getFilePath();
            in=new FileInputStream(new File(filePath));
            byte[] bytes = new byte[in.available()];
            String imageName="001.png";
 /*           //处理IE下载文件的中文名称乱码的问题
            String header = request.getHeader("User-Agent").toUpperCase();
            if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {
                imageName = URLEncoder.encode(imageName, "utf-8");
                imageName = imageName.replace("+", "%20");    //IE下载文件名空格变+号问题
            } else {
                imageName = new String(imageName.getBytes(), "iso-8859-1");
            }*/
            in.read(bytes);
            headers.add("Content-Disposition", "attachment;filename="+imageName);
            entity = new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(in!=null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return entity;
    }

}
