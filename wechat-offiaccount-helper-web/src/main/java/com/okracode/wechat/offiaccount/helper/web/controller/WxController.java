package com.okracode.wechat.offiaccount.helper.web.controller;

import com.okracode.wechat.offiaccount.helper.repository.dao.TextMessageDao;
import com.okracode.wechat.offiaccount.helper.service.CoreService;
import com.soecode.wxtools.api.IService;
import com.soecode.wxtools.api.WxMessageRouter;
import com.soecode.wxtools.api.WxService;
import com.soecode.wxtools.bean.WxXmlMessage;
import com.soecode.wxtools.bean.WxXmlOutMessage;
import com.soecode.wxtools.util.xml.XStreamTransformer;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信订阅号核心控制器
 *
 * @author Eric Ren
 * @version 1.0.0
 * @date 2021/8/17
 */
@RestController
@RequestMapping("/wechat.do")
@Slf4j
public class WxController {

    private final IService iService = new WxService();
    @Resource
    private CoreService coreService;
    @Resource
    private TextMessageDao textMessageDao;

    @GetMapping
    public String check(String signature, String timestamp, String nonce, String echostr) throws InterruptedException {
        log.info("check param signatrue:{}, timestamp:{}, nonce:{}, echostr:{}", signature, timestamp, nonce, echostr);
        if (iService.checkSignature(signature, timestamp, nonce, echostr)) {
            log.info("check success");
            return echostr;
        }
        log.warn("check fail");
        return null;
    }

    @PostMapping
    public void handle(HttpServletRequest request, HttpServletResponse response)
            throws IOException, InterruptedException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        CountDownLatch latch=new CountDownLatch(10);
        for (int j = 0; j < 100; j++) {
            new Thread(()->{

                for (int i = 0; i <= 100; i++) {
                    textMessageDao.selectOneRecvMsg1(i);
                }
                System.out.println(Thread.currentThread().getName());
                latch.countDown();
            }).start();
        }
        latch.await();

        // 创建一个路由器
        try {
            WxMessageRouter router = new WxMessageRouter(iService);
            // 微信服务器推送过来的是XML格式。
            WxXmlMessage wx = XStreamTransformer.fromXml(WxXmlMessage.class, request.getInputStream());
            log.info("接收到的消息：\n{}", wx.toString());
            router.rule().handler(coreService).end();
            // router.rule().event(WxConsts.EVT_CLICK).eventKey(MenuKey.HELP).handler(HelpDocHandler.getInstance()).end();
            // 把消息传递给路由器进行处理
            WxXmlOutMessage xmlOutMsg = router.route(wx);
            if (xmlOutMsg != null)
                out.print(xmlOutMsg.toXml());// 因为是明文，所以不用加密，直接返回给用户。

        } catch (Exception e) {
            log.error("无法应答微信消息", e);
        } finally {
            out.close();
        }
    }
}
