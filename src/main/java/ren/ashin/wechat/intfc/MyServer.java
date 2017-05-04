package ren.ashin.wechat.intfc;

import java.util.concurrent.LinkedBlockingQueue;

import org.aeonbits.owner.ConfigFactory;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import ren.ashin.wechat.intfc.demo.bean.WechatMsg;
import ren.ashin.wechat.intfc.demo.service.QueueConsumerThread;
import ren.ashin.wechat.intfc.util.MainConfig;

/**
 * @ClassName: MyServer
 * @Description: Jetty程序启动入口（调试请从MyServerTest进入）
 * @author renzx
 * @date Apr 11, 2017
 */
public class MyServer {
    public static ApplicationContext ctx = null;
    public static MainConfig mfg = null;
    public static LinkedBlockingQueue<WechatMsg> queue = new LinkedBlockingQueue<WechatMsg>();

    public static void main(String[] args) throws Exception {
        PropertyConfigurator.configure("conf/log4j.properties");
        ctx = new FileSystemXmlApplicationContext("conf/applicationContext.xml");
        mfg = ConfigFactory.create(MainConfig.class);
        int jettyPort = mfg.jettyPort();
        String jettyMode = mfg.jettyMode();

        // 启动数据库数据插入线程
        QueueConsumerThread mcs = new QueueConsumerThread();
        mcs.start();

        String warPath = "wechat-intfc.war";
        String webapp = "src/main/webapp";

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
