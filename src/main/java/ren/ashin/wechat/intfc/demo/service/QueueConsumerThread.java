package ren.ashin.wechat.intfc.demo.service;

import org.apache.log4j.Logger;

import ren.ashin.wechat.intfc.MyServer;
import ren.ashin.wechat.intfc.demo.bean.WechatMsg;
import ren.ashin.wechat.intfc.demo.dao.WechatMsgDao;

/**
 * @ClassName: QueueConsumerThread
 * @Description: TODO
 * @author renzx
 * @date May 4, 2017
 */
public class QueueConsumerThread extends Thread {
    private static final Logger LOG = Logger.getLogger(QueueConsumerThread.class);
    private WechatMsgDao wechatMsgDao = MyServer.ctx.getBean(WechatMsgDao.class);

    @Override
    public void run() {
        while (true) {
            try {
                WechatMsg msg = MyServer.queue.take();
                wechatMsgDao.insertOneMsg(msg);
                LOG.debug("向数据库成功插入一组内容");
            } catch (InterruptedException e) {
                LOG.error("向数据库插入数据出错", e);
            }
        }
    }
}
