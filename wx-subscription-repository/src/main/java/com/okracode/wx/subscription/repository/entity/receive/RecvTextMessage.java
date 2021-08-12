package com.okracode.wx.subscription.repository.entity.receive;

/**
 * @author Eric Ren
 * @ClassName: TextMessage
 * @Description: 文本消息
 * @date May 8, 2017
 */
public class RecvTextMessage extends RecvBaseMessage {

    // 消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
