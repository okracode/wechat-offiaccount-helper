package ren.ashin.wechat.intfc.demo.dao;

import org.apache.ibatis.annotations.Insert;

import ren.ashin.wechat.intfc.demo.bean.WechatMsg;

/**
 * @ClassName: WechatMsgDao
 * @Description: 微信消息DAO
 * @author renzx
 * @date May 4, 2017
 */
public interface WechatMsgDao {

    @Insert("insert into wechat_msg(tousername,fromusername,createtime,msgtype,content,funcflag,msgid)"
            + "value(#{toUserName},#{fromUserName},#{createTime},#{msgType},#{content},#{funcFlag},#{msgId})")
    void insertOneMsg(WechatMsg wechatMsg);
}
