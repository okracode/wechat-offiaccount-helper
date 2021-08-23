package com.okracode.wx.subscription.service.event;

import com.okracode.wx.subscription.repository.dao.TextMessageDao;
import com.okracode.wx.subscription.repository.entity.WechatMsg;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * 事件监听器
 *
 * @author Eric Ren
 * @version 1.0.0
 * @date 2021/8/20
 */
@Component
@Slf4j
@EnableAsync
public class ApplicationEventListener {

    @Resource
    private TextMessageDao textMessageDao;

    //使用注解@Async支持 这样不仅可以支持通过调用，也支持异步调用，非常的灵活，
    @Async
    @EventListener
    public void msgEventListener(WechatMsg wechatMsg) {
        textMessageDao.insertOneRecvMsg(wechatMsg);
        log.debug("向数据库成功插入一组内容");
    }
}
