CREATE TABLE `Feedback` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ContantInfo` varchar(30) DEFAULT NULL,
  `Feedback` varchar(1024) NOT NULL,
  `CreateAt` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8