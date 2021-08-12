package com.okracode.wx.subscription.web.util;

import com.okracode.wx.subscription.web.bean.recv.RecvTextMessage;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 数据日志队列
 *
 * @author Eric Ren
 * @version 1.0.0
 * @date 2021/8/12
 */
public class DataQueue {

    public static LinkedBlockingQueue<RecvTextMessage> queue = new LinkedBlockingQueue<RecvTextMessage>();

}
