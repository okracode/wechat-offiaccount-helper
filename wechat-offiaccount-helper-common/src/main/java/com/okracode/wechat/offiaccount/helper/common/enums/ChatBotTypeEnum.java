package com.okracode.wechat.offiaccount.helper.common.enums;

import lombok.Getter;

/**
 * 聊天机器人类型
 *
 * @author Eric Ren
 * @version 1.0.0
 * @date 2021/8/20
 */
@Getter
public enum ChatBotTypeEnum {
    TU_LING_123(0),
    QING_YUN_KE(1);

    private final int typeCode;

    ChatBotTypeEnum(int typeCode) {
        this.typeCode = typeCode;
    }
}
