/*
Navicat MySQL Data Transfer

Source Server         : roger
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : spider

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-04-07 19:47:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `publisher`
-- ----------------------------
DROP TABLE IF EXISTS `publisher`;
CREATE TABLE `publisher` (
  `id` int(8) NOT NULL,
  `username` varchar(32) NOT NULL,
  `isVerified` tinyint(1) NOT NULL DEFAULT '0',
  `authInfo` varchar(256) DEFAULT NULL,
  `avatarUrl` varchar(512) DEFAULT NULL,
  `isFollowed` tinyint(1) NOT NULL DEFAULT '0',
  `followerCount` bigint(12) NOT NULL DEFAULT '0',
  `verifiedContent` varchar(512) DEFAULT NULL,
  `description` blob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of publisher
-- ----------------------------
