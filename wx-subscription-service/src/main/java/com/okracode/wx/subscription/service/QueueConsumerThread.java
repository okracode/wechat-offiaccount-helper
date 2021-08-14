package com.okracode.wx.subscription.service;

import com.okracode.wx.subscription.repository.dao.TextMessageDao;
import com.okracode.wx.subscription.repository.entity.receive.RecvTextMessage;
import com.okracode.wx.subscription.service.queue.DataQueue;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Eric Ren
 * @ClassName: QueueConsumerThread
 * @Description: TODO
 * @date May 4, 2017
 */
@Component
@Slf4j
public class QueueConsumerThread {

    @Resource
    private TextMessageDao textMessageDao;

    public QueueConsumerThread() {
        new Thread(() -> {
            while (true) {
                try {
                    RecvTextMessage msg = DataQueue.queue.take();
                    textMessageDao.insertOneRecvMsg(msg);
                    log.debug("向数据库成功插入一组内容");
                } catch (InterruptedException e) {
                    log.error("向数据库插入数据出错", e);
                }
            }
        }).start();
    }
}
