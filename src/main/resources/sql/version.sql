CREATE TABLE `Version` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Version` varchar(45) NOT NULL,
  `isForce` tinyint(1) NOT NULL DEFAULT '0',
  `Comments` varchar(255) NOT NULL,
  `Linkurl` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4