/*
SQLyog Ultimate v8.55 
MySQL - 5.6.21-log : Database - ty1501j
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ty1501j` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `ty1501j`;

/*Table structure for table `ttsft_ds` */

DROP TABLE IF EXISTS `ttsft_ds`;

CREATE TABLE `ttsft_ds` (
  `dsid` varchar(32) COLLATE utf8_bin NOT NULL,
  `dsmc` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `dsbm` varchar(64) COLLATE utf8_bin NOT NULL,
  `dslx` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `dsdc` varchar(64) COLLATE utf8_bin NOT NULL,
  `dsip` varchar(32) COLLATE utf8_bin NOT NULL,
  `dsport` int(5) NOT NULL,
  `dsnm` varchar(64) COLLATE utf8_bin NOT NULL,
  `dsurl` varchar(512) COLLATE utf8_bin NOT NULL,
  `dsuser` varchar(32) COLLATE utf8_bin NOT NULL,
  `dspass` varchar(16) COLLATE utf8_bin NOT NULL,
  `dsmin` int(5) DEFAULT '1',
  `dsmax` int(5) DEFAULT '2',
  `dsinit` int(5) DEFAULT '1',
  `dsinc` int(5) DEFAULT '1',
  `xh` double(5,2) DEFAULT '1.00',
  `qybz` char(1) COLLATE utf8_bin NOT NULL DEFAULT 'N',
  `dslb` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`dsid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `ttsft_ds` */

/*Table structure for table `ttsft_dst` */

DROP TABLE IF EXISTS `ttsft_dst`;

CREATE TABLE `ttsft_dst` (
  `tid` varchar(32) COLLATE utf8_bin NOT NULL,
  `tmc` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `tlx` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `tdm` varchar(64) COLLATE utf8_bin NOT NULL,
  `bz` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `dsid` varchar(32) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`tid`),
  KEY `FK_ttsft_dst` (`dsid`),
  CONSTRAINT `FK_ttsft_dst` FOREIGN KEY (`dsid`) REFERENCES `ttsft_ds` (`dsid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `ttsft_dst` */

/*Table structure for table `ttsft_dstc` */

DROP TABLE IF EXISTS `ttsft_dstc`;

CREATE TABLE `ttsft_dstc` (
  `cid` varchar(32) COLLATE utf8_bin NOT NULL,
  `cmc` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `cdm` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `clx` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `cfkbz` char(1) COLLATE utf8_bin DEFAULT 'Y',
  `ckd` int(11) DEFAULT NULL,
  `cjd` int(11) DEFAULT NULL,
  `tid` varchar(32) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`cid`),
  KEY `FK_ttsft_dstc` (`tid`),
  CONSTRAINT `FK_ttsft_dstc` FOREIGN KEY (`tid`) REFERENCES `ttsft_dst` (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `ttsft_dstc` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
