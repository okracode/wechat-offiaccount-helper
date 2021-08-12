package com.okracode.wx.subscription.repository.entity.send;


/**
 * @author Eric Ren
 * @ClassName: MusicMessage
 * @Description: 音乐消息
 * @date May 8, 2017
 */
public class SendMusicMessage extends SendBaseMessage {

    // 音乐
    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        Music = music;
    }
}
