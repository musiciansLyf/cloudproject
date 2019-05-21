package com.example.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.MyFile;
import com.example.mapper.FileMapper;
import com.example.service.FileService;
import org.springframework.stereotype.Service;

/**
 * @ClassName liyufeng
 * @Description TODO
 * @Author Administrator
 * @Date 2019/5/13 20:30
 * @Version 1.0
 **/
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, MyFile> implements FileService {
}
