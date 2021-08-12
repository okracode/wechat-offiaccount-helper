package com.okracode.wx.subscription.repository.dao;

import org.apache.ibatis.annotations.Insert;

import com.okracode.wx.subscription.repository.entity.receive.RecvTextMessage;

/**
 * @ClassName: WechatMsgDao
 * @Description: 微信消息DAO
 * @author renzx
 * @date May 4, 2017
 */
public interface TextMessageDao {

    @Insert("insert into wechat_msg(tousername,fromusername,createtime,msgtype,content,msgid)"
            + "value(#{toUserName},#{fromUserName},#{createTime},#{msgType},#{content},#{msgId})")
    void insertOneRecvMsg(RecvTextMessage recvTextMessage);
}
