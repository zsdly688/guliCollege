package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OSSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags ="阿里云文件管理")
@CrossOrigin
@RestController
@RequestMapping("/eduoss/fileoss")
public class OSSController {
    @Autowired
    private OSSService ossService;
    //上传头像的方法
    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    public R uploadOssFile( MultipartFile file){
       String url= ossService.uploadFileAvatar(file);
        return R.ok().message("文件上传成功").data("url",url);
    }

}
