package com.okracode.wx.subscription.repository.entity.receive;

/**
 * @ClassName: VoiceMessage
 * @Description: 音频消息
 * @author renzx
 * @date May 8, 2017
 */
public class RecvVoiceMessage extends RecvBaseMessage {
    // 媒体ID
    private String MediaId;
    // 语音格式
    private String Format;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }
}
