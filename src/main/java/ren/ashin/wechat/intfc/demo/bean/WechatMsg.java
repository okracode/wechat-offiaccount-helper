package ren.ashin.wechat.intfc.demo.bean;

import java.util.Date;

/**
 * @ClassName: WechatMsg
 * @Description: 微信消息对象
 * @author renzx
 * @date May 4, 2017
 */
public class WechatMsg {
    private Long id;
    private String toUserName;
    private String fromUserName;
    private Date createTime;
    private String msgType;
    private String content;
    private String funcFlag;
    private String msgId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFuncFlag() {
        return funcFlag;
    }

    public void setFuncFlag(String funcFlag) {
        this.funcFlag = funcFlag;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

}
