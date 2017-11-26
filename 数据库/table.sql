/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.6.21-log : Database - zyb_agv
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`zyb_agv` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `zyb_agv`;

/*Table structure for table `tb_agv` */

DROP TABLE IF EXISTS `tb_agv`;

CREATE TABLE `tb_agv` (
  `id`      int(11) NOT NULL AUTO_INCREMENT,
  `name`    varchar(40) DEFAULT NULL,
  `online`  INTEGER DEFAULT 0, 
  `forbidden` INTEGER DEFAULT 0, 
  `typeId` int(11) NOT NULL,
  `createDate` datetime DEFAULT NULL,
  `createUserid` int(11) NOT NULL,
  `state`   varchar(40) DEFAULT NULL COMMENT 'stop:停止, move：运行, charge:充电, free:空闲',
  `taskId` int(11) NOT NULL,
  `x`       int(11) DEFAULT NULL COMMENT  'pixel',
  `y`       int(11) DEFAULT NULL COMMENT  'pixel',
  `station1` int(11) DEFAULT NULL COMMENT '哪两个点中间',
  `station2` int(11) DEFAULT NULL,  
  `percent`  DOUBLE DEFAULT NULL COMMENT '百分比',
  `dirX`     INTEGER DEFAULT NULL COMMENT '方向x',
  `dirY`     INTEGER DEFAULT NULL COMMENT '方向y',
  `macAddr` varchar(40) NOT NULL COMMENT 'mac地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_agv` */

insert  into `tb_agv`(`id`,`name`,`online`,`forbidden`,`typeId`,`createDate`,`createUserid`,`state`,`station1`) values 
(0,'未分配',0,1,1,'2016-01-01 00:00:00',1,'free',0),
(1,'AGV001',1,0,1,'2016-03-03 20:30:31',1,'free',1),
(3,'AGV003',0,0,1,'2016-03-03 20:30:31',1,'free',1),
(4,'AGV004',1,0,1,'2016-03-03 20:30:31',1,'free',1);

/*Table structure for table `tb_task` */

DROP TABLE IF EXISTS `tb_task`;

CREATE TABLE `tb_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `startPos` int(11) DEFAULT NULL COMMENT '出发点编号',
  `endPos` int(11) DEFAULT NULL COMMENT '到达点编号',
  `priority` int(11) DEFAULT NULL COMMENT '优先级',
  `scheduleTime` datetime DEFAULT NULL COMMENT '何时完成',
  `typeId`  INTEGER DEFAULT NULL COMMENT '0:移动,1:运输,2:货架',
  `state` varchar(100) DEFAULT NULL COMMENT 'waiting:等待,heading:前往取货,loading:取货,loadingFinish:取货完毕,moving:运行,unloading:卸货,unloadingFinish:卸货完毕,cancelled:取消,finished:正常结束',
  `ifRead`  INTEGER NOT NULL COMMENT '是否被下位机读取',
  `agvId`   int(11) DEFAULT NULL,
  `length`   int(11) DEFAULT NULL COMMENT '毫米',
  `width`    int(11) DEFAULT NULL COMMENT '毫米',
  `height`   int(11) DEFAULT NULL COMMENT '毫米',
  `weight`   int (11) DEFAULT NULL COMMENT 'g',
  `agvTypeId` INTEGER DEFAULT NULL,
  
  `shelf1Id` int(11) DEFAULT NULL COMMENT '起始货架Id',
  `shelf1Row` int(11) DEFAULT NULL COMMENT '货架第几行',
  `shelf1Column` int(11) DEFAULT NULL COMMENT '货架第几列',
  
  `shelf2Id` int(11) DEFAULT NULL COMMENT '终止货架Id',
  `shelf2Row` int(11) DEFAULT NULL COMMENT '货架第几行',
  `shelf2Column` int(11) DEFAULT NULL COMMENT '货架第几列',
    
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `createUserid` int(11) NOT NULL,
  
  `startDate`  datetime DEFAULT NULL COMMENT '开始时间',
  `startLoadDate`  datetime DEFAULT NULL COMMENT '开始装载时间',
  `endLoadDate`  datetime DEFAULT NULL COMMENT '结束装载时间',
  `startUnloadDate`  datetime DEFAULT NULL COMMENT '开始卸载时间',
  `endUnloadDate`  datetime DEFAULT NULL COMMENT '结束卸载时间',
  `endDate`  datetime DEFAULT NULL COMMENT '结束时间',
  
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_task` */

insert  into `tb_task`(`id`,`name`,`startPos`,`endPos`,`priority`,`scheduleTime`,`typeId`,`state`,`ifRead`,
`agvId`,`length`,`width`,`height`,`weight`,`agvTypeId`,`createDate`,`createUserid`) values 
(0,'无任务',NULL,NULL,1,'2016-01-01 00:00:00',1,'',1,0,NULL,NULL,NULL,NULL,1,'2016-01-01 00:00:00',1),
(1,'T001',1,2,1,'2016-01-06 00:03:00',1,'waiting',0,1,NULL,NULL,NULL,NULL,1,'2016-03-06 13:45:40',1),
(2,'T002',1,2,1,'2016-01-06 00:03:00',1,'waiting',0,3,NULL,NULL,NULL,NULL,1,'2016-03-06 13:45:40',1),
(3,'T003',1,2,1,'2016-01-06 00:03:00',1,'waiting',0,4,NULL,NULL,NULL,NULL,1,'2016-03-06 13:45:40',1),
(4,'T004',1,2,1,'2016-01-06 00:03:00',1,'finished',0,1,NULL,NULL,NULL,NULL,1,'2016-03-06 13:45:40',1),
(5,'T005',1,2,1,'2016-01-06 00:03:00',1,'finished',0,3,NULL,NULL,NULL,NULL,1,'2016-03-06 13:45:40',1),
(6,'T006',1,2,1,'2016-01-06 00:03:00',1,'finished',0,4,NULL,NULL,NULL,NULL,1,'2016-03-06 13:45:40',1),
(7,'T001',1,2,1,'2016-01-06 00:03:00',1,'waiting',0,1,NULL,NULL,NULL,NULL,1,'2016-03-06 13:45:40',2),
(8,'T002',1,2,1,'2016-01-06 00:03:00',1,'finished',0,3,NULL,NULL,NULL,NULL,1,'2016-03-06 13:45:40',2);

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(40) DEFAULT NULL COMMENT '用户名',
  `login_name` varchar(40) NOT NULL COMMENT 	'登录名',
  `user_type` varchar(10) DEFAULT NULL COMMENT '用户类型,person为普通，super为管理员，system为系统不可用于登入',
  `user_status` int(11) DEFAULT '0' COMMENT '用户状态，1为有效，0为无效',
  `user_password` varchar(200) NOT NULL COMMENT '用户密码',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_userid` int(11) DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tb_user` */

insert  into `tb_user`(`user_id`,`user_name`,`login_name`,`user_type`,`user_status`,`user_password`,`create_date`,`create_userid`) values 
(0,'系统','system','system',0,'e10adc3949ba59abbe56e057f20f883e',NULL,NULL),
(1,'管理员','admin','super',1,'e10adc3949ba59abbe56e057f20f883e',NULL,NULL),
(2,'user','user','person',1,'ee11cbb19052e40b07aac0ca060c23ee',NULL,NULL);

DROP TABLE IF EXISTS `tb_agv_type`;

CREATE TABLE `tb_agv_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) DEFAULT NULL COMMENT 'agv类型名',
  `length` int(11) DEFAULT NULL COMMENT '载货尺寸 长',
  `width` int(11) DEFAULT NULL COMMENT '载货尺寸 宽',
  `height` int(11) DEFAULT NULL COMMENT '载货尺寸 高 mm',
  `weight` int(11) DEFAULT NULL COMMENT '载货重量 g',
  `maxVel` float DEFAULT NULL COMMENT '最大速度 m/s',
  `turnVel` float DEFAULT NULL COMMENT '转弯速度 m/s',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4;

insert  into `tb_agv_type` (`id`,`name`,`length`,`width`,`height`,`weight`,`maxVel`,`turnVel`) values 
(1,'小型',150,150,150,3000,1.4,90),
(2,'中型',300,300,300,6000,3.2,60),
(3,'大型',600,600,600,9000,3,50),
(4,'叉车',600,600,600,9000,3,50);

DROP TABLE IF EXISTS `tb_log`;
CREATE TABLE `tb_log` (  
	`id` int(11) NOT NULL AUTO_INCREMENT,
    `userId` INTEGER  DEFAULT NULL ,  
    `class`  varchar(255) DEFAULT NULL COMMENT '类名',  
    `method` varchar(255) DEFAULT NULL COMMENT '方法名',  
    `createDate` datetime DEFAULT NULL COMMENT '产生时间 ',
    `logLevel` varchar(20) DEFAULT NULL COMMENT '日志级别',  
    `msg` varchar(555) DEFAULT NULL COMMENT '日志信息',  
    PRIMARY KEY (`id`)
)  ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4; 
insert  into `tb_log` (`id`,`userId`,`class`,`method`,`createDate`,`logLevel`,`msg`) values 
(1,1,"agv.logger","hello()","2016-01-06 00:03:00","DEBUG","helloworl");

DROP TABLE IF EXISTS `tb_shelf`;
CREATE TABLE `tb_shelf` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sn` varchar(225) NOT NULL COMMENT 'sn编号',
  `height` int(11) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  `boxHeight` int(11) DEFAULT NULL COMMENT '格子高度',
  `boxWidth` int(11) DEFAULT NULL COMMENT '格子宽度',
  `rowNum` int(11) DEFAULT NULL COMMENT '货架几行',
  `columnNum` int(11) DEFAULT NULL COMMENT '货架几列',
  `thickness` int(11) DEFAULT NULL COMMENT '木板厚度',
  `baseHeight` int(11) DEFAULT NULL COMMENT '底座高度',
  `occupied` int(11) NOT NULL DEFAULT '0' COMMENT '货架是否被占用',
  `createDate` datetime DEFAULT NULL COMMENT '创建时间',
  `createUserid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
insert into `tb_shelf` (`id`,`sn`,`height`,`width`,`boxHeight`,`boxWidth`,`rowNum`,`columnNum`,`thickness`,`baseHeight`,`occupied`,`createDate`,`createUserId` ) values
(0,"SH000",0,0,0,0,0,0,0,0,0,"2016-01-06 00:03:00",0),
(1,"SH001",0,0,0,0,1,1,0,0,0,"2016-01-06 00:03:00",1),
(2,"SH002",0,0,0,0,1,1,0,0,0,"2016-01-06 00:03:00",1),
(3,"SH003",0,0,0,0,1,1,0,0,0,"2016-01-06 00:03:00",1),
(4,"SH004",0,0,0,0,1,1,0,0,0,"2016-01-06 00:03:00",1),
(5,"SH005",100,100,50,50,2,2,0,0,0,"2016-01-06 00:03:00",1),
(6,"SH006",100,100,50,50,2,2,0,0,0,"2016-01-06 00:03:00",1);


/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
