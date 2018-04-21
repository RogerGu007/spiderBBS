CREATE TABLE `Smsmessage` (
  `PhoneNumber` varchar(20) CHARACTER SET latin1 NOT NULL,
  `Code` varchar(7) CHARACTER SET latin1 NOT NULL,
  `CreateAt` datetime NOT NULL,
  `CreateBy` varchar(45) CHARACTER SET latin1 NOT NULL,
  `UpdateAt` datetime NOT NULL,
  `UpdateBy` varchar(45) CHARACTER SET latin1 NOT NULL,
  `Count` int(11) NOT NULL,
  PRIMARY KEY (`PhoneNumber`),
  KEY `IDX_PHONE_UpdateAt` (`PhoneNumber`,`UpdateAt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8