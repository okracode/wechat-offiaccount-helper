package com.okracode.wx.subscription.service.event;

import com.okracode.wx.subscription.repository.entity.receive.RecvTextMessage;
import org.springframework.context.ApplicationEvent;

/**
 * 微信消息事件
 *
 * @author Eric Ren
 * @version 1.0.0
 * @date 2021/8/20
 */
public class MsgEvent extends ApplicationEvent {

    private RecvTextMessage recvTextMessage;

    public MsgEvent(Object source, RecvTextMessage recvTextMessage) {
        super(source);
        this.recvTextMessage = recvTextMessage;
    }

    public RecvTextMessage getRecvTextMessage() {
        return recvTextMessage;
    }

    public void setRecvTextMessage(RecvTextMessage recvTextMessage) {
        this.recvTextMessage = recvTextMessage;
    }
}
