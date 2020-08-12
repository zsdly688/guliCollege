package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.atguigu.oss.service.OSSService;
import com.atguigu.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
@Service
public class OSSServiceImpl implements OSSService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName=ConstantPropertiesUtil.BUCKET_NAME;
        try {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            //判断oss实例是否存在：如果不存在则创建，如果存在则获取
         if (!ossClient.doesBucketExist(bucketName)) {
            //创建bucket
            ossClient.createBucket(bucketName);
            //设置oss实例的访问权限：公共读
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        }
        // 上传文件流。
        InputStream inputStream= file.getInputStream();
        String fileName=file.getOriginalFilename();
        String uuid= UUID.randomUUID().toString().replace("-","");

        String dataPath=new DateTime().toString("yyyy/MM/dd");
        fileName=dataPath+"/"+uuid+fileName;
        ossClient.putObject(bucketName, fileName, inputStream);

// 关闭OSSClient。
        ossClient.shutdown();

        String url="https://"+bucketName+"."+endpoint+"/"+fileName;

        return url;
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }



    }
}
