package com.okracode.wx.subscription.repository.dao;

import com.okracode.wx.subscription.repository.entity.WechatMsg;
import org.apache.ibatis.annotations.Insert;

/**
 * @author Eric Ren
 * @ClassName: WechatMsgDao
 * @Description: 微信消息DAO
 * @date May 4, 2017
 */
public interface TextMessageDao {

    @Insert("insert into wechat_msg("
            + "tousername, "
            + "fromusername, "
            + "msg_time, "
            + "chat_bot_type, "
            + "msgtype, "
            + "content, "
            + "funcflag, "
            + "msgid)"
            + "value("
            + "#{toUserName}, "
            + "#{fromUserName}, "
            + "#{msgTime}, "
            + "#{chatBotType}, "
            + "#{msgType}, "
            + "#{content}, "
            + "#{funcFlag}, "
            + "#{msgId})")
    void insertOneRecvMsg(WechatMsg wechatMsg);
}
