package com.okracode.wx.subscription.web;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.util.ResourceUtils;

/**
 * @ClassName: MyServer
 * @Description: Eclipse 调试入库
 * @author renzx
 * @date Apr 11, 2017
 */
public class MyServerTest {
    public static void main(String[] args) throws Exception {
        PropertyConfigurator.configure(ResourceUtils.getFile("classpath:log4j.properties").getPath());
        String webapp = "src/main/webapp";

//        Server server = new Server(80);
//
//        WebAppContext context = new WebAppContext();
//
//        // 使用war包打包前，请注释掉一下两行
//        context.setDescriptor(webapp + "/WEB-INF/web.xml");
//        context.setResourceBase(webapp);
//        // 使用Eclipse调试时，请把该行注释掉
//        // context.setWar(warPath);
//
//        context.setContextPath("/");
//        context.setClassLoader(Thread.currentThread().getContextClassLoader());
//
//        server.setHandler(context);
//
//        server.start();
//        server.join();
    }
}
