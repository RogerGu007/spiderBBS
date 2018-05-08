CREATE TABLE `BeAdmin` (
  `ID` int(11) NOT NULL,
  `userName` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `sessionID` varchar(128) DEFAULT NULL,
  `UpdateAt` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `userName_UNIQUE` (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4