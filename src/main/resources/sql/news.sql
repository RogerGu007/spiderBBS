/*
Navicat MySQL Data Transfer

Source Server         : roger
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : spider

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-04-06 14:59:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `news`
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Subject` varchar(512) NOT NULL,
  `NewsType` tinyint(4) NOT NULL,
  `NewsSubType` tinyint(4) DEFAULT NULL,
  `PostDate` datetime DEFAULT NULL,
  `LocationCode` tinyint(4) DEFAULT NULL,
  `isHot` tinyint(4) DEFAULT NULL,
  `CreateAt` datetime NOT NULL,
  `CreateBy` varchar(45) CHARACTER SET latin1 NOT NULL,
  `linkUrl` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IDX_LINKURL` (`linkUrl`) USING BTREE,
  KEY `UK_SUBJECT` (`Subject`),
  KEY `IDX_TYPE_SUBTYPE` (`NewsType`,`NewsSubType`),
  KEY `IDX_CREATEDATE` (`CreateAt`),
  KEY `IDX_LOCATION` (`LocationCode`)
) ENGINE=InnoDB AUTO_INCREMENT=1963 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news
-- ----------------------------