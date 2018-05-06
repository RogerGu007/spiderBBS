SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `FirstLevelComment`
-- ----------------------------
DROP TABLE IF EXISTS `FirstLevelComment`;

CREATE TABLE `FirstLevelComment` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `newsID` int(11) unsigned NOT NULL,
  `Subject` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL,
  `userID` int(11) unsigned NOT NULL,
  `nickName` varchar(145) CHARACTER SET utf8 NOT NULL,
  `avatarUrl` varchar(255) DEFAULT NULL,
  `comment` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `count` int(11) NOT NULL DEFAULT '1',
  `createAt` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_NEWSID` (`newsID`),
  KEY `IDX_USERID` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci