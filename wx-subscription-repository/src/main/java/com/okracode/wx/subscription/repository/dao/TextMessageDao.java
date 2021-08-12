package com.okracode.wx.subscription.repository.dao;

import com.okracode.wx.subscription.repository.entity.receive.RecvTextMessage;
import org.apache.ibatis.annotations.Insert;

/**
 * @author Eric Ren
 * @ClassName: WechatMsgDao
 * @Description: 微信消息DAO
 * @date May 4, 2017
 */
public interface TextMessageDao {

    @Insert("insert into wechat_msg(tousername,fromusername,createtime,msgtype,content,msgid)"
            + "value(#{toUserName},#{fromUserName},#{createTime},#{msgType},#{content},#{msgId})")
    void insertOneRecvMsg(RecvTextMessage recvTextMessage);
}
