CREATE TABLE `User` (
  `ID` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `PhoneNumber` varchar(15) DEFAULT NULL,
  `NickName` varchar(45) NOT NULL,
  `avatarUrl` varchar(255) DEFAULT NULL,
  `isVerified` tinyint(4) NOT NULL DEFAULT '0',
  `CreateAt` datetime NOT NULL,
  `CreateBy` varchar(45) NOT NULL,
  `UpdateAt` datetime NOT NULL,
  `UpdateBy` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `NickName_UNIQUE` (`NickName`),
  UNIQUE KEY `UK_PhoneNumber` (`PhoneNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8