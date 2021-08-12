package com.okracode.wx.subscription.web;

import com.okracode.wx.subscription.web.util.ParseJson;
import java.io.FileNotFoundException;
import org.apache.log4j.PropertyConfigurator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.ResourceUtils;

@SpringBootApplication
@ComponentScan("com.okracode.wx.subscription")
@MapperScan("com.okracode.wx.subscription.web.dao")
public class WxSubscriptionWebApplication {

    public static void main(String[] args) {
        try {
            PropertyConfigurator.configure(ResourceUtils.getFile("classpath:log4j.properties").getPath());
            // 读取城市编码文件
            ParseJson.parseJsonFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SpringApplication.run(WxSubscriptionWebApplication.class, args);
    }

}
