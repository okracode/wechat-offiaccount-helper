package com.okracode.wx.subscription.service.event;

import com.okracode.wx.subscription.repository.entity.receive.RecvTextMessage;
import javax.annotation.Resource;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 事件发布类
 *
 * @author Eric Ren
 * @version 1.0.0
 * @date 2021/8/20
 */
@Component
public class MsgEventPublisher {

    @Resource
    private ApplicationContext applicationContext;

    public void publish(RecvTextMessage recvTextMessage) {
        applicationContext.publishEvent(new MsgEvent(this, recvTextMessage));
    }
}
