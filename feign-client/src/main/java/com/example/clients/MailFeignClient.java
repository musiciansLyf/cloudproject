package com.example.clients;

import com.example.entity.MyFile;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户Http请求的客户端。
 * 注解FeignClient的传参：表示的是注册到 Eureka 服务上的模块名称，也就是需要访问的微服务名称。
 * @author lyf
 * @version 0.0.1
 * @date 17/9/19
 *
 */
//url解决feign.FeignException: status 404 reading xxService#xxmethod(Integer)，如果不加,默认找的是服务器端的 ,所以找不到你刚新建的接口 ps feignclient也要注册
@FeignClient(name="mailservice")
public interface MailFeignClient {
    @RequestLine(value = "POST /mail/mailservice")//使用spring的注解@RequestParam 会报参数太多错误
    String sendMail(@Param("sendTo") String sendTo, @Param("titel") String titel, @Param("content") String content,
                    @Param("file") MyFile[] file, @Param("isHtml") Boolean isHtml);

}
