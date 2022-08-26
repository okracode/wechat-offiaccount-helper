package com.okracode.wechat.offiaccount.helper.repository.dao;

import com.okracode.wechat.offiaccount.helper.repository.entity.WechatMsg;
import org.apache.ibatis.annotations.Select;

/**
 * 文本信息扩展DAO
 *
 * @author Eric Ren
 * @version 1.0.0
 * @date 2021/8/25
 */
public interface TextMessageExtDao {
    @Select("select * from wechat_msg")
    WechatMsg selectOneRecvMsg();

    @Select("UPDATE config SET age = age+#{plusValue} WHERE id=1")
    void selectOneRecvMsg1(int plusValue);
}
