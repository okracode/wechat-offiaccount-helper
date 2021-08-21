package com.okracode.wx.subscription.web;

import com.okracode.wx.subscription.service.util.ParseJson;
import java.io.IOException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan("com.okracode.wx.subscription")
@MapperScan("com.okracode.wx.subscription.repository.dao")
@EnableAsync
public class WxSubscriptionWebApplication {

    public static void main(String[] args) {
        try {
            // 读取城市编码文件
            ParseJson.parseJsonFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SpringApplication.run(WxSubscriptionWebApplication.class, args);
    }

}
