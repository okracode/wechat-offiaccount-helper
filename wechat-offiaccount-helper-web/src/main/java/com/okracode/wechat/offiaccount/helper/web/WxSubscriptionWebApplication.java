package com.okracode.wechat.offiaccount.helper.web;

import com.okracode.wechat.offiaccount.helper.service.util.ParseJson;
import java.io.IOException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.okracode.wechat.offiaccount.helper")
@MapperScan("com.okracode.wechat.offiaccount.helper.repository.dao")
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
