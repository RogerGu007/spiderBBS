SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `SecondLevelComment`
-- ----------------------------
DROP TABLE IF EXISTS `SecondLevelComment`;

CREATE TABLE `SecondLevelComment` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `flID` int(11) unsigned DEFAULT NULL,
  `fromUserID` int(11) unsigned NOT NULL,
  `fromUserNickName` varchar(145) CHARACTER SET utf8 NOT NULL,
  `fromAvatarUrl` varchar(255) DEFAULT NULL,
  `toUserID` int(11) unsigned DEFAULT NULL,
  `toUserNickName` varchar(145) CHARACTER SET utf8 NOT NULL,
  `toAvatarUrl` varchar(255) DEFAULT NULL,
  `replyComment` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `createAt` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_FLC` (`flID`),
  KEY `IDX_TOUSERID` (`toUserID`),
  CONSTRAINT `FK_FLID` FOREIGN KEY (`flID`) REFERENCES `FirstLevelComment` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci