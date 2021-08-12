package com.okracode.wx.subscription.repository.entity.receive;

/**
 * @author Eric Ren
 * @ClassName: ImageMessage
 * @Description: 图片消息
 * @date May 8, 2017
 */
public class RecvImageMessage extends RecvBaseMessage {

    // 图片链接
    private String PicUrl;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }
}
