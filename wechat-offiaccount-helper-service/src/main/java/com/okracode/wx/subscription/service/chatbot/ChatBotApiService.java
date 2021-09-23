package com.okracode.wx.subscription.service.chatbot;

import com.okracode.wx.subscription.common.enums.ChatBotTypeEnum;

/**
 * 聊天机器人接口
 *
 * @author Eric Ren
 * @version 1.0.0
 * @date 2021/8/13
 */
public interface ChatBotApiService {

    /**
     * 调用图灵机器人api接口，获取智能回复内容，解析获取自己所需结果
     * @return
     */
    String callOpenApi(String content);

    ChatBotTypeEnum getChatBotType();
}
