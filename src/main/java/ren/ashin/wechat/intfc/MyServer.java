package ren.ashin.wechat.intfc;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * @ClassName: MyServer
 * @Description: Jetty程序启动入口
 * @author renzx
 * @date Apr 11, 2017
 */
public class MyServer {
    public static void main(String[] args) throws Exception {
        String warPath = "wechat-intfc.war";
        String webapp = "src/main/webapp";

        Server server = new Server(80);

        WebAppContext context = new WebAppContext();

        // 使用war包打包前，请注释掉一下两行
//        context.setDescriptor(webapp + "/WEB-INF/web.xml");
//        context.setResourceBase(webapp);
        // 使用Eclipse调试时，请把该行注释掉
         context.setWar(warPath);

        context.setContextPath("/");
        context.setClassLoader(Thread.currentThread().getContextClassLoader());

        server.setHandler(context);

        server.start();
        server.join();
    }
}
