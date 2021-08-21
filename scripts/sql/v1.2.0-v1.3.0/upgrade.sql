USE
    `wechat`;

ALTER TABLE `wechat_msg`
    MODIFY COLUMN `createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '消息时间旧值';
UPDATE `wechat_msg` SET `msg_time` = `createtime` WHERE `msg_time` != `createtime`;