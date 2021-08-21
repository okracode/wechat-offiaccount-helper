SET
    FOREIGN_KEY_CHECKS = 0;
CREATE
    DATABASE IF NOT EXISTS `wechat`;
USE
    `wechat`;
-- ----------------------------
-- Table structure for wechat_msg
-- ----------------------------
DROP TABLE IF EXISTS `wechat_msg`;
CREATE TABLE `wechat_msg`
(
    `id`            int(11)      NOT NULL AUTO_INCREMENT,
    `tousername`    varchar(255) NOT NULL,
    `fromusername`  varchar(255) NOT NULL,
    `createtime`    datetime              DEFAULT CURRENT_TIMESTAMP COMMENT '消息时间旧值',
    `msg_time`      datetime(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '消息时间',
    `chat_bot_type` smallint              DEFAULT NULL COMMENT 'NULL表示未调用聊天机器人。聊天机器人类型：0-图灵机器人，1-青云客',
    `msgtype`       varchar(255) NOT NULL,
    `content`       varchar(255) NOT NULL,
    `funcflag`      varchar(255)          DEFAULT NULL,
    `msgid`         varchar(255)          DEFAULT NULL,
    `create_time`   datetime(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '数据创建时间',
    `update_time`   datetime(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '数据修改时间'
        PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
