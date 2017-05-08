package ren.ashin.wechat.intfc.service;

import org.apache.log4j.Logger;

import ren.ashin.wechat.intfc.WeChatServer;
import ren.ashin.wechat.intfc.bean.recv.RecvTextMessage;
import ren.ashin.wechat.intfc.dao.TextMessageDao;

/**
 * @ClassName: QueueConsumerThread
 * @Description: TODO
 * @author renzx
 * @date May 4, 2017
 */
public class QueueConsumerThread extends Thread {
    private static final Logger LOG = Logger.getLogger(QueueConsumerThread.class);
    private TextMessageDao wechatMsgDao = WeChatServer.ctx.getBean(TextMessageDao.class);

    @Override
    public void run() {
        while (true) {
            try {
                RecvTextMessage msg = WeChatServer.queue.take();
                wechatMsgDao.insertOneRecvMsg(msg);
                LOG.debug("向数据库成功插入一组内容");
            } catch (InterruptedException e) {
                LOG.error("向数据库插入数据出错", e);
            }
        }
    }
}
