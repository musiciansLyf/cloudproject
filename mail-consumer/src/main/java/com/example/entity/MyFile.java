package com.example.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName liyufeng
 * @Description TODO
 * @Author Administrator
 * @Date 2019/5/13 17:52
 * @Version 1.0
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

public class MyFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *文件主键
     **/
    private String fileId;
    /**
     *文件名称
     **/
    private String fileName;
    /**
     * 文件初始名称
     **/
    private String fileOriginate;
    /**
     *文件路径
     **/
    private String filePath;
    /**
     *文件类型
     **/
    private String fileContentType;
}
