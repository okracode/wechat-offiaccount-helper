package com.okracode.wx.subscription.service;

import com.okracode.wx.subscription.repository.dao.*;
import com.okracode.wx.subscription.repository.entity.receive.RecvTextMessage;
import com.okracode.wx.subscription.service.queue.DataQueue;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author renzx
 * @ClassName: QueueConsumerThread
 * @Description: TODO
 * @date May 4, 2017
 */
@Component
public class QueueConsumerThread {

    private static final Logger LOG = Logger.getLogger(QueueConsumerThread.class);
    @Resource
    private TextMessageDao textMessageDao;

    public QueueConsumerThread() {
        new Thread(() -> {
            while (true) {
                try {
                    RecvTextMessage msg = DataQueue.queue.take();
                    textMessageDao.insertOneRecvMsg(msg);
                    LOG.debug("向数据库成功插入一组内容");
                } catch (InterruptedException e) {
                    LOG.error("向数据库插入数据出错", e);
                }
            }
        }).start();
    }
}
