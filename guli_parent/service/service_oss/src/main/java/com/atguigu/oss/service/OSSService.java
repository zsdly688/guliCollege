package com.atguigu.oss.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface OSSService {

    String uploadFileAvatar(MultipartFile file);
}
