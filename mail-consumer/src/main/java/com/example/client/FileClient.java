package com.example.client;

import com.example.config.LogConfig;
import feign.Param;

import feign.Response;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @ClassName liyufeng
 * @Description TODO
 * @Author Administrator
 * @Date 2019/5/16 14:18
 * @Version 1.0
 **/
@FeignClient(value = "file-service",fallback = LogConfig.class)//在网络请求时，可能会出现异常请求，如果还想再异常情况下使系统可用，那么就需要容错处理,这里是debug
public interface FileClient {
    @RequestMapping(value = "/file/download",method= RequestMethod.POST )
    Response download(@RequestParam("fileId") String fileId);
}