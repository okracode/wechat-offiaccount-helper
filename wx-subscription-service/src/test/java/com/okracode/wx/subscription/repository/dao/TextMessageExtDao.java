package com.okracode.wx.subscription.repository.dao;

import com.okracode.wx.subscription.repository.entity.WechatMsg;
import org.apache.ibatis.annotations.Select;

/**
 * 文本信息扩展DAO
 *
 * @author Eric Ren
 * @version 1.0.0
 * @date 2021/8/30
 */
public interface TextMessageExtDao {
    @Select("select * from wechat_msg")
    WechatMsg selectOneRecvMsg();
}
