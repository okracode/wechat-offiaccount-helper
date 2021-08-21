package com.okracode.wx.subscription.service.event;

import com.okracode.wx.subscription.repository.entity.WechatMsg;
import org.springframework.context.ApplicationEvent;

/**
 * 微信消息事件
 *
 * @author Eric Ren
 * @version 1.0.0
 * @date 2021/8/20
 */
public class MsgEvent extends ApplicationEvent {

    private WechatMsg wechatMsg;

    public MsgEvent(Object source, WechatMsg wechatMsg) {
        super(source);
        this.wechatMsg = wechatMsg;
    }

    public WechatMsg getWechatMsg() {
        return wechatMsg;
    }

    public void setWechatMsg(WechatMsg wechatMsg) {
        this.wechatMsg = wechatMsg;
    }
}
