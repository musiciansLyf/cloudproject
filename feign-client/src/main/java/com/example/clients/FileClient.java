package com.example.clients;

import com.example.entity.MyFile;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName liyufeng
 * @Description TODO
 * @Author Administrator
 * @Date 2019/5/16 14:18
 * @Version 1.0
 **/
@FeignClient(value = "file-service")
public interface FileClient {
    @RequestLine(value = "POST /file")
    List<MyFile> upfile(@Param("file") MultipartFile[] file);

    @RequestLine(value = "POST /file/download")
    ResponseEntity<byte[]> download(@Param("fileId") String fileId);
}
