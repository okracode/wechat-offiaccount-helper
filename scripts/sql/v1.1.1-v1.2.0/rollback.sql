USE
    `wechat`;

ALTER TABLE `wechat_msg`
    DROP COLUMN `msg_time`;
ALTER TABLE `wechat_msg`
    DROP COLUMN `chat_bot_type`;
ALTER TABLE `wechat_msg`
    DROP COLUMN `create_time`;
ALTER TABLE `wechat_msg`
    DROP COLUMN `update_time`;