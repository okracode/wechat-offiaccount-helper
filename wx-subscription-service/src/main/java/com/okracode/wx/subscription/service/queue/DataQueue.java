package com.okracode.wx.subscription.service.queue;

import com.okracode.wx.subscription.repository.entity.WechatMsg;
import com.okracode.wx.subscription.repository.entity.receive.RecvTextMessage;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 数据日志队列
 *
 * @author Eric Ren
 * @version 1.0.0
 * @date 2021/8/12
 */
public class DataQueue {

    public static LinkedBlockingQueue<WechatMsg> queue = new LinkedBlockingQueue<WechatMsg>();

}
