package com.okracode.wechat.offiaccount.helper.repository.entity.send;

/**
 * @author Eric Ren
 * @ClassName: TextMessage
 * @Description: 文本消息
 * @date May 8, 2017
 */
public class SendTextMessage extends SendBaseMessage {

    // 回复的消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
