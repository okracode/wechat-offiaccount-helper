package com.okracode.wx.subscription.web;

import com.okracode.wx.subscription.web.util.ParseJson;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.log4j.PropertyConfigurator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@SpringBootApplication
@ComponentScan("com.okracode.wx.subscription")
@MapperScan("com.okracode.wx.subscription.web.dao")
public class WxSubscriptionWebApplication {

    public static void main(String[] args) {
        try {
            Resource log4jRes = new ClassPathResource("log4j.properties");
            Properties properties = new Properties();
            properties.load(log4jRes.getInputStream());
            PropertyConfigurator.configure(properties);
            // 读取城市编码文件
            ParseJson.parseJsonFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SpringApplication.run(WxSubscriptionWebApplication.class, args);
    }

}
