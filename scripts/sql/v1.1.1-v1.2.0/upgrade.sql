USE
    `wechat`;

ALTER TABLE `wechat_msg`
    ADD COLUMN `msg_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '消息时间' AFTER `createtime`;
ALTER TABLE `wechat_msg`
    ADD COLUMN `chat_bot_type` smallint DEFAULT '1' COMMENT 'NULL表示未调用聊天机器人。聊天机器人类型：0-图灵机器人，1-青云客' AFTER `msg_time`;
ALTER TABLE `wechat_msg`
    ADD COLUMN `create_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '数据创建时间' AFTER `msgid`;
ALTER TABLE `wechat_msg`
    ADD COLUMN `update_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '数据修改时间' AFTER `create_time`;