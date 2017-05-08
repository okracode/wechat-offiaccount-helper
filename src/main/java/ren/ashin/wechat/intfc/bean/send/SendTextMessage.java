package ren.ashin.wechat.intfc.bean.send;

/**
 * @ClassName: TextMessage
 * @Description: 文本消息
 * @author renzx
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
