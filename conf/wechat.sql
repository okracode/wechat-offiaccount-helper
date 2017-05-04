/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : wechat

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2017-05-04 15:23:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for wechat_msg
-- ----------------------------
DROP TABLE IF EXISTS `wechat_msg`;
CREATE TABLE `wechat_msg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tousername` varchar(255) NOT NULL,
  `fromusername` varchar(255) NOT NULL,
  `createtime` datetime NOT NULL,
  `msgtype` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL,
  `funcflag` varchar(255) DEFAULT NULL,
  `msgid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
