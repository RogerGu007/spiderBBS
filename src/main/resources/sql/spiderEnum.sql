/*
Navicat MySQL Data Transfer

Source Server         : roger
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : spider

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-04-07 18:18:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `SpiderEnum`
-- ----------------------------
DROP TABLE IF EXISTS `SpiderEnum`;
CREATE TABLE `SpiderEnum` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `enumType` varchar(32) NOT NULL,
  `enumKey` varchar(32) NOT NULL,
  `enumValue` int(8) NOT NULL,
  `enumComment` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `enumType_UNIQUE` (`enumType`,`enumKey`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8

-- ----------------------------
-- Records of SpiderEnum
-- ----------------------------
INSERT INTO `SpiderEnum` VALUES ('1', 'SITE_ENUM', 'TJ_BBS', '211', '同济大学BBS');
INSERT INTO `SpiderEnum` VALUES ('2', 'SITE_ENUM', 'FUDAN_BBS', '212', '复旦大学BBS');
INSERT INTO `SpiderEnum` VALUES ('3', 'SITE_ENUM', 'SJTU_BBS', '213', '上海交通大学BBS');
INSERT INTO `SpiderEnum` VALUES ('4', 'SITE_ENUM', 'SUFE_BBS', '215', '上海财经大学BBS');
INSERT INTO `SpiderEnum` VALUES ('5', 'SITE_ENUM', 'ECUST_BBS', '216', '华东理工大学BBS');
INSERT INTO `SpiderEnum` VALUES ('6', 'SITE_ENUM', 'NJU_BBS', '251', '南京大学BBS');
INSERT INTO `SpiderEnum` VALUES ('7', 'SITE_ENUM', 'SEU_BBS', '252', '东南大学BBS');
INSERT INTO `SpiderEnum` VALUES ('8', 'SITE_ENUM', 'TSING_BBS', '101', '清华大学BBS');
INSERT INTO `SpiderEnum` VALUES ('9', 'SITE_ENUM', 'PEKING_BBS', '102', '北京大学BBS');
INSERT INTO `SpiderEnum` VALUES ('10', 'SITE_ENUM', 'RUC_BBS', '103', '中国人民大学BBS');
INSERT INTO `SpiderEnum` VALUES ('11', 'SITE_ENUM', 'WHU_BBS', '271', '武汉大学BBS');
INSERT INTO `SpiderEnum` VALUES ('12', 'SITE_ENUM', 'HUST_BBS', '272', '华中科技大学BBS');
INSERT INTO `SpiderEnum` VALUES ('13', 'SITE_ENUM', 'USTC_BBS', '5511', '中国科技大学BBS');
INSERT INTO `SpiderEnum` VALUES ('14', 'LOCATION_ENUM', 'BEIJING', '10', '北京');
INSERT INTO `SpiderEnum` VALUES ('15', 'LOCATION_ENUM', 'GUANGDONG', '20', '广州');
INSERT INTO `SpiderEnum` VALUES ('16', 'LOCATION_ENUM', 'SHANGHAI', '21', '上海');
INSERT INTO `SpiderEnum` VALUES ('17', 'LOCATION_ENUM', 'NANJING', '25', '南京');
INSERT INTO `SpiderEnum` VALUES ('18', 'LOCATION_ENUM', 'WUHAN', '27', '武汉');
INSERT INTO `SpiderEnum` VALUES ('19', 'LOCATION_ENUM', 'HEFEI', '551', '合肥');
INSERT INTO `SpiderEnum` VALUES ('20', 'NEWS_TYPE_ENUM', 'NEWS_JOB', '2', '求职分类');
INSERT INTO `SpiderEnum` VALUES ('21', 'NEWS_TYPE_ENUM', 'NEWS_FRIENDS', '3', '交友分类');
INSERT INTO `SpiderEnum` VALUES ('22', 'NEWS_SUB_TYPE_ENUM', 'SUB_FULLTIME', '1', '全职');
INSERT INTO `SpiderEnum` VALUES ('23', 'NEWS_SUB_TYPE_ENUM', 'SUB_PARTTIME', '2', '兼职');
INSERT INTO `SpiderEnum` VALUES ('24', 'NEWS_SUB_TYPE_ENUM', 'SUB_INTERN', '3', '实习');
INSERT INTO `SpiderEnum` VALUES ('25', 'NEWS_SUB_TYPE_ENUM', 'SUB_CAMPUS', '4', '校招');
INSERT INTO `SpiderEnum` VALUES ('26', 'NEWS_SUB_TYPE_ENUM', 'SUB_UPGRADE', '5', '社招');
INSERT INTO `SpiderEnum` VALUES ('27', 'SITE_ENUM', 'ECNU_BBS', '214', '华东师范大学BBS');
