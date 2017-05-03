package ren.ashin.wechat.intfc;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * @ClassName: MyServer
 * @Description: Jetty程序启动入口（调试请从MyServerTest进入）
 * @author renzx
 * @date Apr 11, 2017
 */
public class MyServer {
    public static void main(String[] args) throws Exception {
        PropertyConfigurator.configure("conf/log4j.properties");
        String warPath = "wechat-intfc.war";
        // String webapp = "src/main/webapp";

        Server server = new Server(8080);

        WebAppContext context = new WebAppContext();

        // 使用war包打包前，请注释掉一下两行
        // context.setDescriptor(webapp + "/WEB-INF/web.xml");
        // context.setResourceBase(webapp);
        // 使用Eclipse调试时，请把该行注释掉
        context.setWar(warPath);

        context.setContextPath("/");
        context.setClassLoader(Thread.currentThread().getContextClassLoader());

        server.setHandler(context);

        server.start();
        server.join();
    }
}
