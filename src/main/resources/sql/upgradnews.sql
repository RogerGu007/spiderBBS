//update NewsDetail
ALTER TABLE `spider`.`NewsDetail` 
CHANGE COLUMN `newsID` `newsID` BIGINT UNSIGNED NOT NULL ,
CHANGE COLUMN `sourceArticleUrl` `sourceArticleUrl` VARCHAR(512) NULL ;


//update news
ALTER TABLE `spider`.`News` 
CHANGE COLUMN `ID` `ID` BIGINT UNSIGNED NOT NULL ,
ADD COLUMN `imagePaths` VARCHAR(512) NULL AFTER `Subject`,
ADD COLUMN `hasDetail` TINYINT NOT NULL DEFAULT 1 AFTER `isValid`;

//add fK
ALTER TABLE `spider`.`NewsDetail` 
ADD CONSTRAINT `FK_NEWSID`
  FOREIGN KEY (`newsID`)
  REFERENCES `spider`.`News` (`ID`)
  ON DELETE CASCADE
  ON UPDATE CASCADE;