package com.atguigu.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients        //fegin
@EnableDiscoveryClient     //nacos注册
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu"})
public class EduserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduserviceApplication.class,args);
    }
}
