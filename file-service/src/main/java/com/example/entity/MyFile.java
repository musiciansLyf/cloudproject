package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("file")
public class MyFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     *文件主键
     **/
    @TableId
    private String fileId;
    /**
     *文件名称
     **/
    @TableField
    private String fileName;
    /**
     * 文件初始名称
     **/
    @TableField
    private String fileOriginate;
    /**
     *文件路径
     **/
    @TableField
    private String filePath;
    /**
     *文件类型
     **/
    @TableField
    private String fileContentType;
}
