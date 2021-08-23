USE
    `wechat`;

ALTER TABLE `wechat_msg`
    MODIFY COLUMN `createtime` datetime NOT NULL;