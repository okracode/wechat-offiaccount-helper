package com.okracode.wx.subscription.web;

import java.util.concurrent.LinkedBlockingQueue;

import org.aeonbits.owner.ConfigFactory;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.context.ApplicationContext;

import com.okracode.wx.subscription.web.bean.recv.RecvTextMessage;
import com.okracode.wx.subscription.web.service.QueueConsumerThread;
import com.okracode.wx.subscription.web.util.MainConfig;
import com.okracode.wx.subscription.web.util.ParseJson;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @ClassName: WeChatServer
 * @Description: Jetty程序启动入口
 * @author renzx
 * @date Apr 11, 2017
 */
public class WeChatServer {
    public static ApplicationContext ctx = null;
    public static MainConfig mfg = null;
    public static LinkedBlockingQueue<RecvTextMessage> queue =
            new LinkedBlockingQueue<RecvTextMessage>();

    public static void main(String[] args) throws Exception {
        PropertyConfigurator.configure("conf/log4j.properties");
        ctx = new FileSystemXmlApplicationContext("conf/applicationContext.xml");
        mfg = ConfigFactory.create(MainConfig.class);
        int jettyPort = mfg.jettyPort();
        String jettyMode = mfg.jettyMode();

        // 启动数据库数据插入线程
        QueueConsumerThread mcs = new QueueConsumerThread();
        mcs.start();
        // 读取城市编码文件
        ParseJson.parseJsonFile();

        String warPath = "wx-subscription-web-1.0.0.war";
        String webapp = "wx-subscription-web/src/main/webapp";

        if (jettyPort == 0) {
            jettyPort = 8080;
        }
        Server server = new Server(jettyPort);

        WebAppContext context = new WebAppContext();

        if ("eclipse".equals(jettyMode)) {
            // 使用war包打包前，请注释掉一下两行
            context.setDescriptor(webapp + "/WEB-INF/web.xml");
            context.setResourceBase(webapp);
        } else {
            // 使用Eclipse调试时，请把该行注释掉
            context.setWar(warPath);
        }

        context.setContextPath("/");
        context.setClassLoader(Thread.currentThread().getContextClassLoader());

        server.setHandler(context);

        server.start();
        server.join();
    }
}
