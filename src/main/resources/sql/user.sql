-- ----------------------------
DROP TABLE IF EXISTS `User`;

CREATE TABLE `User` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `PhoneNumber` varchar(15) DEFAULT NULL,
  `NickName` varchar(45) NOT NULL,
  `avatarUrl` varchar(255) DEFAULT NULL,
  `isVerified` tinyint(4) NOT NULL DEFAULT '0',
  `sex` tinyint(4) DEFAULT NULL,
  `college` varchar(255) DEFAULT NULL,
  `CreateAt` datetime NOT NULL,
  `CreateBy` varchar(45) NOT NULL,
  `UpdateAt` datetime NOT NULL,
  `UpdateBy` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `NickName_UNIQUE` (`NickName`),
  UNIQUE KEY `UK_PhoneNumber` (`PhoneNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8