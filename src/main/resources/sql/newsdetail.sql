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
-- Table structure for `NewsDetail`
-- ----------------------------
DROP TABLE IF EXISTS `NewsDetail`;

CREATE TABLE `NewsDetail` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `newsID` int(10) unsigned NOT NULL,
  `sourceArticleUrl` varchar(512) NOT NULL,
  `detailContent` text,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `newsID_UNIQUE` (`newsID`),
  UNIQUE KEY `LinkUrl_UNIQUE` (`sourceArticleUrl`) USING BTREE,
  CONSTRAINT `FK_NewsID` FOREIGN KEY (`newsID`) REFERENCES `News` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

-- ----------------------------
-- Records of newsdetail
-- ----------------------------
