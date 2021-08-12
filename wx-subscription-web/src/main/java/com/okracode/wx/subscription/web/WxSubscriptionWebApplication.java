package com.okracode.wx.subscription.web;

import com.okracode.wx.subscription.web.util.ParseJson;
import org.apache.log4j.PropertyConfigurator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.okracode.wx.subscription")
@MapperScan("com.okracode.wx.subscription.web.dao")
public class WxSubscriptionWebApplication {

    public static void main(String[] args) {
        PropertyConfigurator.configure("conf/log4j.properties");
        // 读取城市编码文件
        ParseJson.parseJsonFile();
        SpringApplication.run(WxSubscriptionWebApplication.class, args);
    }

}
