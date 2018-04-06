/*
Navicat MySQL Data Transfer

Source Server         : roger
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : spider

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-04-06 15:00:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `newsdetail`
-- ----------------------------
DROP TABLE IF EXISTS `newsdetail`;
CREATE TABLE `newsdetail` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `newsID` varchar(32) NOT NULL,
  `sourceArticleUrl` varchar(512) NOT NULL,
  `detailContent` text,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `newsID_UNIQUE` (`newsID`),
  UNIQUE KEY `LinkUrl_UNIQUE` (`sourceArticleUrl`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=221 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of newsdetail
-- ----------------------------
