package com.okracode.wx.subscription.web.controller;

import com.okracode.wx.subscription.common.SignUtil;
import com.okracode.wx.subscription.common.conf.CommonConfig;
import com.okracode.wx.subscription.service.CoreService;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Eric Ren
 * @ClassName: CoreController
 * @Description: 核心请求处理类
 * @date May 8, 2017
 */
@RestController
@Slf4j
public class CoreController {
    @Resource
    private CoreService coreService;
    @Resource
    private CommonConfig commonConfig;

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

        log.debug("signature:" + signature + "\ntimestamp:" + timestamp + "\nnonce:" + nonce
                + "\nechostr:" + echostr);
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(commonConfig.getToken(), signature, timestamp, nonce)) {
            return echostr;
        }
        return null;
    }

    /**
     * 处理微信服务器发来的消息
     */
    @PostMapping("wechat.do")
    public String doPost(HttpServletRequest request) throws IOException {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        return coreService.processRequest(request);
    }

}
