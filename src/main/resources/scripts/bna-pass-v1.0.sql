CREATE SCHEMA IF NOT EXISTS `bna` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CREATE TABLE `bna`.`LOCALITY` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(50) NULL,
  `CITY` VARCHAR(50) NULL,
  PRIMARY KEY (`ID`)) 
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

CREATE TABLE `bna`.`ADDRESS` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `ADDRESS` VARCHAR(100) NULL,
  `LOCALITY_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  CONSTRAINT `ADDRESS_LOCALITY_FK`
    FOREIGN KEY (`LOCALITY_ID`)
    REFERENCES `bna`.`LOCALITY` (`ID`)) 
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

  
CREATE TABLE `bna`.`CONTACT` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `PHONE` VARCHAR(15) NOT NULL,
  `EMAIL` VARCHAR(40) NULL,
  PRIMARY KEY (`ID`)) 
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;
  
CREATE TABLE `bna`.`USER` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(40) NOT NULL,
  `ADDRESS_ID` INT NOT NULL,
  `CONTACT_ID` INT NOT NULL,
  `IMAGE_URL` VARCHAR(100) NOT NULL,
  `THUMBNAIL` VARCHAR(100) NOT NULL,
  `TYPE`  VARCHAR(20) NOT NULL,
  `IS_ACTIVE` BIT NULL DEFAULT 1,
  `CREATED_DATETIME` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_DATETIME` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  
  PRIMARY KEY (`ID`),
  CONSTRAINT `MERCHANT_ADDRESS_FK`
    FOREIGN KEY (`ADDRESS_ID`)
    REFERENCES `bna`.`ADDRESS` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `MERCHANT_CONTACT_FK`
    FOREIGN KEY (`CONTACT_ID`)
    REFERENCES `bna`.`CONTACT` (`ID`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

CREATE TABLE `bna`.`MEMBER` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `FIRST_NAME` VARCHAR(50) NOT NULL,
  `LAST_NAME` VARCHAR(30) NULL,
  `IMAGE_URL` VARCHAR(100) NULL,
  `PHONE` VARCHAR(15) NULL,
  `EMAIL` VARCHAR(40) NULL,
  `ID_PROOF` VARCHAR(40) NULL,
  `ADDRESS` VARCHAR(40) NULL,
  `SCAN_CODE` varchar(50) NOT NULL,
  `CREATED_DATETIME` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_DATETIME` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IDX_UNQ_SCAN_CODE` (`SCAN_CODE`))
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

CREATE TABLE `bna`.`DESCRIPTION` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` VARCHAR(100) NOT NULL,
  `CREATED_DATETIME` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_DATETIME` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)) 
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

CREATE TABLE `bna`.`MEMBERSHIP` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `USER_ID` INT NOT NULL,
  `MEMBER_ID` INT NOT NULL,
  `STATUS` VARCHAR(10) NOT NULL,
  `DESCRIPTION_ID` INT NULL,
  `DAY_TYPE` VARCHAR(20) NOT NULL,
  `ENTRIES_PER_DAY` INT NOT NULL,
  `START_DATE` TIMESTAMP NOT NULL,
  `END_DATE` TIMESTAMP NOT NULL,
  `CREATED_DATETIME` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_DATETIME` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IDX_MEMBERSHIP_USER_MEMBER` (`USER_ID`,`MEMBER_ID`),
  CONSTRAINT `MEMBERSHIP_USER_FK`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `bna`.`USER` (`ID`)
    ON UPDATE CASCADE,
  CONSTRAINT `MEMBERSHIP_MEMBER_FK`
    FOREIGN KEY (`MEMBER_ID`)
    REFERENCES `bna`.`MEMBER` (`ID`)
    ON UPDATE CASCADE,
  CONSTRAINT `MEMBERSHIP_DESC_FK`
    FOREIGN KEY (`DESCRIPTION_ID`)
    REFERENCES `bna`.`DESCRIPTION` (`ID`)
    ON UPDATE CASCADE)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;

CREATE TABLE `bna`.`SCAN` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `USER_ID` INT NOT NULL,
  `MEMBER_ID` INT NOT NULL,
  `CREATED_DATETIME` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IDX_SCAN_USER_MEMBER` (`USER_ID`,`MEMBER_ID`),
  CONSTRAINT `SCAN_USER_FK`
    FOREIGN KEY (`USER_ID`)
    REFERENCES `bna`.`USER` (`ID`)
    ON UPDATE CASCADE,
  CONSTRAINT `SCAN_MEMBER_FK`
    FOREIGN KEY (`MEMBER_ID`)
    REFERENCES `bna`.`MEMBER` (`ID`)
    ON UPDATE CASCADE)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DEFAULT
AUTO_INCREMENT=1;