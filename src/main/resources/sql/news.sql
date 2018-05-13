/*
Navicat MySQL Data Transfer

Source Server         : roger
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : spider

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-04-22 12:25:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `news`
-- ----------------------------
DROP TABLE IF EXISTS `News`;

CREATE TABLE `News` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Subject` varchar(512) NOT NULL,
  `NewsType` tinyint(4) NOT NULL,
  `NewsSubType` tinyint(4) DEFAULT NULL,
  `PostDate` datetime DEFAULT NULL,
  `LocationCode` int(8) DEFAULT NULL,
  `isHot` tinyint(4) DEFAULT NULL,
  `linkUrl` varchar(512) DEFAULT NULL,
  `CreateAt` datetime NOT NULL,
  `CreateBy` varchar(45) NOT NULL,
  `UpdateAt` datetime DEFAULT NULL,
  `UpdateBy` varchar(45) DEFAULT NULL,
  `publisherId` int(10) unsigned NOT NULL,
  `isValid` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IDX_LINKURL` (`linkUrl`) USING BTREE,
  KEY `UK_SUBJECT` (`Subject`),
  KEY `IDX_CREATEDATE` (`CreateAt`),
  KEY `FK_PublishID` (`publisherId`),
  KEY `IDX_LOCATION` (`LocationCode`,`isValid`),
  KEY `IDX_TYPE_SUBTYPE_locate` (`NewsType`,`NewsSubType`,`LocationCode`,`isValid`),
  KEY `IDX_TYPE_LOCATION` (`NewsType`,`LocationCode`,`isValid`),
  CONSTRAINT `FK_PublishID` FOREIGN KEY (`publisherId`) REFERENCES `User` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='News info'