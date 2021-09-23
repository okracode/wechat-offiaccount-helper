package com.okracode.wx.subscription.repository.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信消息持久化
 *
 * @author Eric Ren
 * @version 1.0.0
 * @date 2021/8/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatMsg {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 开发者微信号
     */
    private String toUserName;
    /**
     * 发送方帐号（一个OpenID）
     */
    private String fromUserName;
    /**
     * 消息时间
     */
    private Date msgTime;
    /**
     * NULL表示未调用聊天机器人。聊天机器人类型：0-图灵机器人，1-青云客
     *
     * @see com.okracode.wx.subscription.common.enums.ChatBotTypeEnum
     */
    private Integer chatBotType;
    /**
     * 消息类型（text/image/location/link）
     */
    private String msgType;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 接口标记
     */
    private String funcFlag;
    /**
     * 消息id，64位整型
     */
    private Long msgId;
    /**
     * 数据创建时间
     */
    private Date createTime;
    /**
     * 数据修改时间
     */
    private Date updateTime;

}
