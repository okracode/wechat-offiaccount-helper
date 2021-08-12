package com.okracode.wx.subscription.web.servlet;

import com.okracode.wx.subscription.web.service.CoreService;
import com.okracode.wx.subscription.web.util.SignUtil;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author renzx
 * @ClassName: CoreServlet
 * @Description: 核心请求处理类
 * @date May 8, 2017
 */
@RestController
public class CoreServlet {

    private static final Logger LOG = Logger.getLogger(CoreServlet.class);

    /**
     * 确认请求来自微信服务器
     */
    @GetMapping("wechat.do")
    public String doGet(HttpServletRequest request) {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        LOG.debug("signature:" + signature + "\ntimestamp:" + timestamp + "\nnonce:" + nonce
                + "\nechostr:" + echostr);
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }
        return null;
    }

    /**
     * 处理微信服务器发来的消息
     */
    @PostMapping("/*")
    public String doPost(HttpServletRequest request) throws IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        return CoreService.processRequest(request);
    }

}
