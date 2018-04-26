SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `FirstLevelComment`
-- ----------------------------
DROP TABLE IF EXISTS `FirstLevelComment`;

CREATE TABLE `FirstLevelComment` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `newsID` int(11) unsigned NOT NULL,
  `userID` int(11) unsigned NOT NULL,
  `nickName` varchar(145) NOT NULL,
  `avatarUrl` varchar(255) DEFAULT NULL,
  `comment` varchar(500) NOT NULL,
  `createAt` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_NEWSID` (`newsID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8