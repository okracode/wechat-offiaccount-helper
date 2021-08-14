package com.okracode.wx.subscription.service;

import com.okracode.wx.subscription.repository.entity.receive.RecvTextMessage;
import com.okracode.wx.subscription.service.util.MessageUtil;
import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Eric Ren
 * @ClassName: CoreService
 * @Description: 核心服务类
 * @date May 8, 2017
 */
@Service
@Slf4j
public class CoreService {

    @Resource
    private TextService textService;

    /**
     * 处理微信发来的请求
     *
     * @param request
     * @return
     */
    public String processRequest(HttpServletRequest request) {
        String respMessage = null;
        try {
            // 默认返回的文本消息内容
            String respContent = "请求处理异常，请稍候尝试！";

            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);

            // MsgId
            long msgId = Long.valueOf(requestMap.get("MsgId"));
            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 消息时间
            String createTimeStr = requestMap.get("CreateTime");
            Date createTime = new Date(Long.valueOf(createTimeStr) * 1000L);

            // 文本消息
            if (msgType.equals(MessageUtil.RECV_MESSAGE_TYPE_TEXT)) {
                // 消息内容
                String content = requestMap.get("Content");
                RecvTextMessage recvTextMessage = new RecvTextMessage();
                recvTextMessage.setContent(content);
                recvTextMessage.setCreateTime(createTime);
                recvTextMessage.setFromUserName(fromUserName);
                recvTextMessage.setMsgId(msgId);
                recvTextMessage.setMsgType(msgType);
                recvTextMessage.setToUserName(toUserName);

                log.debug("接收到的文本消息内容：" + content);
                respMessage = textService.processMsg(recvTextMessage);
            }
            // 图片消息
            else if (msgType.equals(MessageUtil.RECV_MESSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息！";
            }
            // 地理位置消息
            else if (msgType.equals(MessageUtil.RECV_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";
            }
            // 链接消息
            else if (msgType.equals(MessageUtil.RECV_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";
            }
            // 音频消息
            else if (msgType.equals(MessageUtil.RECV_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是音频消息！";
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.RECV_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "谢谢您的关注！";
                }
                // 取消订阅
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // TODO 自定义菜单权没有开放，暂不处理该类消息
                }
            }

            log.debug("返回的消息：" + respMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return respMessage;
    }
}
