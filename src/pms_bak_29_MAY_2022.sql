-- MySQL dump 10.17  Distrib 10.3.20-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: pms
-- ------------------------------------------------------
-- Server version	10.3.20-MariaDB-1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bug`
--

DROP TABLE IF EXISTS `bug`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bug` (
  `bug_id` int(11) NOT NULL AUTO_INCREMENT,
  `bug_name` varchar(200) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `reported_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `project_id` int(11) DEFAULT 1,
  `sevr` int(11) DEFAULT NULL,
  PRIMARY KEY (`bug_id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `bug_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bug`
--

LOCK TABLES `bug` WRITE;
/*!40000 ALTER TABLE `bug` DISABLE KEYS */;
INSERT INTO `bug` VALUES (1,'EPWINEEDCJDIZIHPCGWBKQUSRV','ODZWPOUUBTQOJSWYJAUEERCKFXNWKZPRXHUJLHXDYXQTMMFLNUMZKHAIRTK','2022-05-27 08:53:33',2,3),(2,'LBCHJALMPEWKHUNZHOZWPERUVK','NCSCHVJYZSGDGDLTWTZIFQVOQMKLJRREHRJSKTNGSYGLUECGGZIAUMPIAAO','2022-05-27 08:53:33',5,1),(3,'JJSCTZTUCQGMCTGUMETAWEGIJQ','ANEDAETSDIAIPAZPJUBGFPJAFBMJTYSUIGZFTKTFJYWYEPHSCZHNNNIKLOX','2022-05-27 08:53:33',7,5),(4,'MAGOTVMRYLXVYXLRNIIFQMMDZO','LPBASEXJDLZVPAEHPSGPUFAALCOTUNDPMZJJKQHQTHMFVQUQUPQTDORGCYP','2022-05-27 08:53:33',5,1),(5,'NLKWNCDTJTMDJLWLEEGKRZWXHB','HARGMCVHGWFDFIUAINTFASPWGTZIJIAECITVUKHCSTJKXQZYBWVRASWDUPC','2022-05-27 08:53:33',1,2),(6,'VEROKRBVTZAMZGEYBXAUFFVSAD','EXQPYDORWKZDFOZROXPTDMDNWUYMEEMAJLHHJUQKSFLSNWUPUELDHKIRZET','2022-05-27 08:53:33',7,2),(7,'BSAGYNXLUEFEWJXNOOXCNAUIGH','UFTBZCVHJERHUSKHSXXYSSPEWKCLJTQRRMBIPYIEKEUGLKAOEROPCSDKAYM','2022-05-27 08:53:33',4,5),(8,'ELFGCFGAWXKQSXJMXNXALXOBEV','LVRZUGGLTNIBYZNABOCBDKFLNWPOAOYZYZKESZDRYIAFGYRCXRZBYHURTQM','2022-05-27 08:53:33',8,4),(9,'ALUBMIIXNNMQGQMAHTEXAEDOOO','PYIOOREJIVIQKUDFQLXKMKASCRMTDOGMZTIVKLGPZHAAORYBECQDZNJWSDB','2022-05-27 08:53:33',6,3),(10,'RMBLQTDSNVHHVBPXOYBNZZDHSW','CLHGDSOLOQFUEJHCHFZBHLASTLDTVUDAVECVYWXZCIKHLOBOBOBAYXVSIGF','2022-05-27 08:53:33',6,5),(11,'FUCK','CUMMING','2022-05-28 06:34:57',1,1);
/*!40000 ALTER TABLE `bug` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bug_sevr`
--

DROP TABLE IF EXISTS `bug_sevr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bug_sevr` (
  `sevr_id` int(11) NOT NULL AUTO_INCREMENT,
  `sevr_title` varchar(30) NOT NULL,
  PRIMARY KEY (`sevr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bug_sevr`
--

LOCK TABLES `bug_sevr` WRITE;
/*!40000 ALTER TABLE `bug_sevr` DISABLE KEYS */;
INSERT INTO `bug_sevr` VALUES (1,'LOW'),(2,'MEDIUM'),(3,'HIGH'),(4,'EXTREME'),(5,'EMERGENCY');
/*!40000 ALTER TABLE `bug_sevr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `project_id` int(11) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(200) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `created_by` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL,
  PRIMARY KEY (`project_id`),
  KEY `created_by` (`created_by`),
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`created_by`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'UYHQBXJZJWOU','2022-05-27 08:37:35',12,1),(2,'ADWSRUOXWXUO','2022-05-27 08:37:35',13,5),(3,'JFDVWZFWKJFZ','2022-05-27 08:37:35',12,2),(4,'HJLDUCVJGVMM','2022-05-27 08:37:35',12,5),(5,'PHVMQBXEXEFW','2022-05-27 08:37:35',10,4),(6,'PBBKVVCPRPGS','2022-05-27 08:37:35',10,5),(7,'QJOGZHZFXWDQ','2022-05-27 08:37:35',12,1),(8,'HFTBSXFRDFPX','2022-05-27 08:37:35',11,4),(9,'WEBXYOEVSKUO','2022-05-27 08:37:35',11,5),(10,'VKJOYJFORKNZ','2022-05-27 08:37:37',13,3),(11,'FUCK','2022-05-27 10:21:15',2,1),(12,'FUCK','2022-05-27 10:31:02',2,1),(13,'FUCK','2022-05-27 10:32:13',1,1),(14,'FUCK','2022-05-28 05:58:16',1,2),(15,'FUCK','2022-05-28 06:03:34',1,1),(16,'FUCK','2022-05-28 06:05:27',1,1),(17,'FUCK','2022-05-28 06:07:31',1,1),(18,'FUCK','2022-05-28 06:08:09',1,1),(19,'FUCK','2022-05-28 06:08:45',1,1),(20,'FUCK','2022-05-28 06:09:27',1,1),(21,'FUCK','2022-05-28 17:52:37',1,1);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `role_id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `role_title` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'USER'),(2,'MANAGER'),(3,'ADMIN');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status` (
  `status_id` int(11) NOT NULL AUTO_INCREMENT,
  `status_title` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (1,'OPENED'),(2,'IN_PROGRESS'),(3,'COMPLETED'),(4,'REVIEWED'),(5,'CLOSED');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(200) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `reported_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `project_id` int(11) NOT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`task_id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `task_ibfk_1` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (1,'IGIJSEUYYSWMJBQKCJPRBUZUYH','XMXYDQTZESAQCFXVSZUBGZGSVEUJUVUARVOKNMHNSWBJSXTLLWZLRHQGNTG','2019-06-02 19:20:15',10,5),(2,'AVRHGYSVUEPKYXXXEFRHFBGDHQ','TJEOEVRECPXNJNUNPTDGEDGLGDUYZTKDMSSFAJFBGBUNIBJMDSNUYQZWFBA','2020-01-12 13:43:05',10,3),(3,'HDZQDDWPJXHXXOVIGHJHVEYONH','FYJXXXBJSBXQSVWNZZZNNLBUMRIWVSMKDWYHHDCVQQVRSAESZMCHMQCRXKZ','2019-10-28 20:40:02',1,1),(4,'IRRCVMJHFSSVMWLSSKXYHYDORV','RLMJRIJAABODLTDPJTFPEURGRYGXODRNADJUHDJVKKXSURALFGMCSCPGLLL','2019-06-15 13:50:11',10,5),(5,'IMHBESNTGNETHUYSMHEKXVZJGQ','FTAZBUAXBVTSZFWIKHRIMQAESIGXFRCXJFQCCWHPRCNRPMKFDHNJIBJUGAX','2020-05-07 17:27:02',1,4),(6,'ZNJQNZAZVCCCGLXIJWWRXESSOU','STREDVQXDVJNYZWAKORGGJOWNCOCYYOMAOGNHPGIGKZDQKMEPAEZYIWTEEN','2019-09-11 20:51:58',6,4),(7,'YFSRNCUEKTQDRDWWVHNBQXLODW','SXAYXZPKRLEAESFNGPZBHNJYTOTNQYBWPZUIEORJLMJTXAERCWWFDPNTVHO','2020-01-04 09:26:57',8,2),(8,'QSUGXDZMAFTLOFKJLYEWFAHQGS','VBORFQATOZRIPAYFXYUELGKWXPEVROEBWAOWOSJSFSIRIGBAJWAXSRARTRM','2020-10-26 06:39:13',2,1),(9,'ASPMXVJALRLTDTRVWIRZEGMZXL','JURUPYOPBFWLKITTYQVYQZGOODEAPVUABZDJORACXWQJHRYFWMUNDKXZKQN','2019-09-22 21:13:56',4,3),(10,'GGFCHQMKJDGWKIPNRJPGFIRMJL','DHGSDTEJZQTFECAMCMLSHAMAIEOFXUQDWJWVXOQSOWNMMUODIBQLQALYWBR','2020-12-23 04:42:31',3,1),(11,'FUCK','CUMMING','2022-05-28 06:56:48',1,1);
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `role_id` tinyint(4) DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `join_date` date DEFAULT NULL,
  `last_login` date DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Nevile','Beves','nbeves0@uol.com.br',2,'2013-08-15','2021-09-18','2021-10-30','123456789'),(2,'Beaufort','Broyd','bbroyd1@harvard.edu',2,'2002-06-18','2021-09-20','2022-03-20','123456789'),(3,'Clementia','Willishire','cwillishire2@moonfruit.com',1,'2007-06-11','2021-01-31','2022-06-14','123456789'),(4,'Phyllida','Grishunin','pgrishunin3@hibu.com',1,'2001-07-14','2021-11-04','2022-10-09','123456789'),(5,'Mariann','Abrahmson','mabrahmson4@freewebs.com',1,'2004-08-25','2021-12-01','2021-07-01','123456789'),(6,'Lowe','Oxbe','loxbe5@latimes.com',1,'2019-06-21','2021-05-02','2022-06-14','123456789'),(7,'Nichole','Raecroft','nraecroft6@umich.edu',3,'2009-10-04','2022-12-27','2022-08-15','123456789'),(8,'Melisent','Lofty','mlofty7@moonfruit.com',1,'2008-06-06','2021-09-22','2021-06-27','123456789'),(9,'Junie','Cuppleditch','jcuppleditch8@shareasale.com',2,'2018-02-01','2022-09-29','2022-04-27','123456789'),(10,'Drucill','Phelip','dphelip9@cbslocal.com',3,'2016-12-28','2022-05-30','2021-03-04','123456789'),(11,'Othello','Coughlan','ocoughlana@shareasale.com',3,'2015-01-05','2021-12-02','2021-05-23','123456789'),(12,'Arlinda','Large','alargeb@wordpress.org',3,'2000-10-14','2022-03-27','2021-09-15','123456789'),(13,'Eolande','O\'Cooney','eocooneyc@tamu.edu',3,'2002-09-05','2022-10-03','2022-03-29','123456789'),(14,'Roch','Hunsworth','rhunsworthd@mysql.com',1,'2005-04-17','2022-12-11','2022-05-09','123456789'),(15,'Opaline','Fearick','ofearicke@princeton.edu',2,'2018-08-18','2022-10-24','2022-10-14','123456789'),(16,'Britni','Coverdale','bcoverdalef@storify.com',2,'2002-04-08','2021-01-24','2022-12-19','123456789'),(17,'Carolina','Search','csearchg@php.net',3,'2016-09-23','2022-11-19','2022-01-31','123456789'),(18,'Kippar','Faull','kfaullh@yolasite.com',1,'2015-08-03','2022-08-26','2022-10-24','123456789'),(19,'Kienan','Duiguid','kduiguidi@cbsnews.com',2,'2004-08-01','2022-04-10','2022-06-28','123456789'),(20,'Pat','Dowbakin','pdowbakinj@scribd.com',3,'2014-05-20','2021-12-14','2022-01-06','123456789');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_bug`
--

DROP TABLE IF EXISTS `user_bug`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_bug` (
  `user_id` int(11) NOT NULL,
  `bug_id` int(11) NOT NULL,
  KEY `user_id` (`user_id`),
  KEY `bug_id` (`bug_id`),
  CONSTRAINT `user_bug_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `user_bug_ibfk_2` FOREIGN KEY (`bug_id`) REFERENCES `bug` (`bug_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_bug`
--

LOCK TABLES `user_bug` WRITE;
/*!40000 ALTER TABLE `user_bug` DISABLE KEYS */;
INSERT INTO `user_bug` VALUES (6,4),(3,7),(6,3),(8,3),(5,2),(7,10),(7,4),(10,9),(2,9),(8,5);
/*!40000 ALTER TABLE `user_bug` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_proj`
--

DROP TABLE IF EXISTS `user_proj`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_proj` (
  `user_id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  KEY `user_id` (`user_id`),
  KEY `project_id` (`project_id`),
  CONSTRAINT `user_proj_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `user_proj_ibfk_2` FOREIGN KEY (`project_id`) REFERENCES `project` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_proj`
--

LOCK TABLES `user_proj` WRITE;
/*!40000 ALTER TABLE `user_proj` DISABLE KEYS */;
INSERT INTO `user_proj` VALUES (5,7),(4,8),(7,5),(2,8),(2,8),(4,2),(4,9),(7,3),(8,2),(6,8);
/*!40000 ALTER TABLE `user_proj` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_task`
--

DROP TABLE IF EXISTS `user_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_task` (
  `user_id` int(11) NOT NULL,
  `task_id` int(11) NOT NULL,
  KEY `user_id` (`user_id`),
  KEY `task_id` (`task_id`),
  CONSTRAINT `user_task_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `user_task_ibfk_2` FOREIGN KEY (`task_id`) REFERENCES `task` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_task`
--

LOCK TABLES `user_task` WRITE;
/*!40000 ALTER TABLE `user_task` DISABLE KEYS */;
INSERT INTO `user_task` VALUES (3,1),(6,4),(2,6),(1,5),(5,2),(9,9),(3,5),(7,4),(7,4),(9,10);
/*!40000 ALTER TABLE `user_task` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-05-29 12:18:23
