CREATE TABLE `FavoriteNews` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userID` int(11) NOT NULL,
  `newsID` int(11) NOT NULL,
  `createAt` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_USERID_NEWSID` (`userID`,`newsID`),
  KEY `IDX_USERID_ARTICLE_DATE` (`userID`,`newsID`,`createAt`),
  KEY `IDX_NEWSID` (`newsID`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4