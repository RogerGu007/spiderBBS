SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `Postmsg`
-- ----------------------------
DROP TABLE IF EXISTS `Postmsg`;

CREATE TABLE `Postmsg` (
  `ID` int(10) unsigned NOT NULL,
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `imagePaths` varchar(500) DEFAULT NULL,
  `NewsType` tinyint(4) NOT NULL,
  `NewsSubType` tinyint(4) DEFAULT NULL,
  `PostDate` datetime DEFAULT NULL,
  `LocationCode` int(8) DEFAULT NULL,
  `isHot` tinyint(4) DEFAULT NULL,
  `isValid` tinyint(1) NOT NULL DEFAULT '1',
  `UpdateAt` datetime DEFAULT NULL,
  `UpdateBy` varchar(45) DEFAULT NULL,
  `publisherId` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `PT_IDX_LOCATION` (`LocationCode`,`isValid`),
  KEY `PT_IDX_TYPE_SUBTYPE_locate` (`NewsType`,`NewsSubType`,`LocationCode`,`isValid`),
  KEY `PT_IDX_TYPE_LOCATION` (`NewsType`,`LocationCode`,`isValid`),
  KEY `PT_IDX_PostDate` (`PostDate`,`isValid`),
  KEY `PT_IDX_PublishID` (`publisherId`),
  CONSTRAINT `PT_FK_PublishID` FOREIGN KEY (`publisherId`) REFERENCES `User` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Postmsg info'