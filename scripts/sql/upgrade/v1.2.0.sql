USE
`wechat`;

ALTER TABLE `Users`
    ADD COLUMN `chat_bot_type` varchar(64) NOT NULL DEFAULT 'tuling123' COMMENT '聊天机器人类型' AFTER `createtime`;