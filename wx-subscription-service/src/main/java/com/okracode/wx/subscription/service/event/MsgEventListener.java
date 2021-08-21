package com.okracode.wx.subscription.service.event;

import com.okracode.wx.subscription.repository.dao.TextMessageDao;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
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
public class MsgEventListener implements ApplicationListener<MsgEvent> {

    @Resource
    private TextMessageDao textMessageDao;

    //使用注解@Async支持 这样不仅可以支持通过调用，也支持异步调用，非常的灵活，
    @Async
    @Override
    public void onApplicationEvent(MsgEvent event) {
        textMessageDao.insertOneRecvMsg(event.getRecvTextMessage());
        log.debug("向数据库成功插入一组内容");
    }
}
