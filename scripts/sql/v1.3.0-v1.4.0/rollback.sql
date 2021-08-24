USE
    `wechat`;

ALTER TABLE `wechat_msg`
    ADD COLUMN `createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '消息时间旧值' AFTER `fromusername`;