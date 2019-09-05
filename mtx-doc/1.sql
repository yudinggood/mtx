/*
SQLyog  v12.2.6 (64 bit)
MySQL - 5.5.28 : Database - mtx
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mtx` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `mtx`;

/*Table structure for table `system_dic` */

DROP TABLE IF EXISTS `system_dic`;

CREATE TABLE `system_dic` (
  `dic_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dic_pid` int(10) DEFAULT NULL COMMENT '父id',
  `order` int(11) DEFAULT NULL COMMENT '排序',
  `dic_code` varchar(50) DEFAULT NULL COMMENT '字典编码',
  `dic_name` varchar(50) DEFAULT NULL COMMENT '字典名称',
  `edit_date` datetime DEFAULT NULL COMMENT '修改时间',
  `edit_user` int(10) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`dic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `system_dic` */

/*Table structure for table `system_log` */

DROP TABLE IF EXISTS `system_log`;

CREATE TABLE `system_log` (
  `log_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `used_time` int(11) DEFAULT NULL COMMENT '消耗时间',
  `description` varchar(100) DEFAULT NULL COMMENT '日志描述',
  `domain` varchar(200) DEFAULT NULL COMMENT '根路径',
  `uri` varchar(200) DEFAULT NULL COMMENT 'uri',
  `method` varchar(10) DEFAULT NULL COMMENT '请求类型',
  `user_agent` varchar(200) DEFAULT NULL COMMENT '用户标识',
  `ip` varchar(30) DEFAULT NULL COMMENT '用户IP',
  `permission` varchar(100) DEFAULT NULL COMMENT '权限值',
  `parameter` mediumtext COMMENT '请求参数',
  `result` mediumtext COMMENT '响应结果',
  `edit_user` int(10) DEFAULT NULL COMMENT '操作人',
  `edit_date` datetime DEFAULT NULL COMMENT '操作时间',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '1是删除了',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `system_log` */

/*Table structure for table `system_organization` */

DROP TABLE IF EXISTS `system_organization`;

CREATE TABLE `system_organization` (
  `organization_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `organization_pid` int(10) DEFAULT NULL COMMENT '父id',
  `organization_name` varchar(20) DEFAULT NULL COMMENT '组织名称',
  `description` varchar(200) DEFAULT NULL COMMENT '组织描述',
  `edit_date` datetime DEFAULT NULL COMMENT '修改时间',
  `edit_user` int(10) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`organization_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `system_organization` */

/*Table structure for table `system_permission` */

DROP TABLE IF EXISTS `system_permission`;

CREATE TABLE `system_permission` (
  `permission_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `system_id` int(10) DEFAULT NULL COMMENT '系统id',
  `permission_pid` int(10) DEFAULT NULL COMMENT '父id',
  `permission_order` int(10) DEFAULT NULL COMMENT '排序',
  `name` varchar(20) DEFAULT NULL COMMENT '权限名称',
  `permission_value` varchar(50) DEFAULT NULL COMMENT '权限值',
  `uri` varchar(100) DEFAULT NULL COMMENT '路径',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `type` tinyint(4) DEFAULT NULL COMMENT '权限类型0根1目录2菜单3按钮',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态0禁止1正常',
  `menu_level` tinyint(4) DEFAULT NULL COMMENT '菜单层级 0开始 没有则不是菜单',
  `edit_date` datetime DEFAULT NULL COMMENT '修改时间',
  `edit_user` int(10) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `system_permission` */

insert  into `system_permission`(`permission_id`,`system_id`,`permission_pid`,`permission_order`,`name`,`permission_value`,`uri`,`icon`,`description`,`type`,`status`,`menu_level`,`edit_date`,`edit_user`) values 
(1,1,0,1,'系统管理',NULL,NULL,NULL,'系统管理',0,1,NULL,'2018-08-07 19:33:13',NULL),
(2,1,1,1,'权限设置','system:permission:read','system/permission/index','fa fa-calendar','权限设置',2,1,0,'2018-05-24 13:49:18',NULL),
(3,1,1,2,'Swagger查看','system:swagger','swagger-ui.html','fa fa-bullhorn','Swagger查看',2,1,0,'2018-05-28 09:47:58',NULL),
(4,1,1,3,'Druid查看','system:druid','druid/index.html','fa fa-bullhorn','Druid查看',2,1,0,'2018-08-07 16:49:56',NULL);

/*Table structure for table `system_role` */

DROP TABLE IF EXISTS `system_role`;

CREATE TABLE `system_role` (
  `role_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order` int(10) DEFAULT NULL COMMENT '排序',
  `role_name` varchar(20) DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(20) DEFAULT NULL COMMENT '角色编码',
  `description` varchar(200) DEFAULT NULL COMMENT '角色描述',
  `edit_date` datetime DEFAULT NULL COMMENT '修改时间',
  `edit_user` int(10) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `system_role` */

/*Table structure for table `system_role$permission` */

DROP TABLE IF EXISTS `system_role$permission`;

CREATE TABLE `system_role$permission` (
  `role_permission_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(10) DEFAULT NULL COMMENT '角色id',
  `permission_id` int(10) DEFAULT NULL COMMENT '权限id',
  PRIMARY KEY (`role_permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `system_role$permission` */

/*Table structure for table `system_system` */

DROP TABLE IF EXISTS `system_system`;

CREATE TABLE `system_system` (
  `system_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `theme` varchar(50) DEFAULT NULL COMMENT '主题',
  `basepath` varchar(100) DEFAULT NULL COMMENT '根路径',
  `name` varchar(20) DEFAULT NULL COMMENT '系统名称',
  `description` varchar(200) DEFAULT NULL COMMENT '系统描述',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态0黑名单1正常',
  `extend_map` blob COMMENT '扩展字段',
  `edit_date` datetime DEFAULT NULL COMMENT '修改时间',
  `edit_user` int(10) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`system_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `system_system` */

insert  into `system_system`(`system_id`,`theme`,`basepath`,`name`,`description`,`status`,`extend_map`,`edit_date`,`edit_user`) values 
(1,NULL,NULL,'i助理','正式项目',1,NULL,'2018-05-28 10:25:42',NULL);

/*Table structure for table `system_user` */

DROP TABLE IF EXISTS `system_user`;

CREATE TABLE `system_user` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `login_id` varchar(50) DEFAULT NULL COMMENT '登录id 邮箱或手机号',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `salt` varchar(32) DEFAULT NULL COMMENT '密码的盐',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像路径',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `last_ip` varchar(50) DEFAULT NULL COMMENT '最后登录IP',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别',
  `user_state` tinyint(4) DEFAULT NULL COMMENT '状态1正常2封禁',
  `last_login` datetime DEFAULT NULL COMMENT '最后登录时间',
  `edit_date` datetime DEFAULT NULL COMMENT '修改时间',
  `edit_user` int(10) DEFAULT NULL COMMENT '修改人',
  `extend_map` blob COMMENT '扩展字段',
  `deleted` tinyint(4) DEFAULT NULL COMMENT '1是删除了',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `system_user` */

/*Table structure for table `system_user$organization` */

DROP TABLE IF EXISTS `system_user$organization`;

CREATE TABLE `system_user$organization` (
  `user_organization_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(10) DEFAULT NULL COMMENT '用户id',
  `organization_id` int(10) DEFAULT NULL COMMENT '组织id',
  PRIMARY KEY (`user_organization_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `system_user$organization` */

/*Table structure for table `system_user$permission` */

DROP TABLE IF EXISTS `system_user$permission`;

CREATE TABLE `system_user$permission` (
  `user_permission_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(10) DEFAULT NULL COMMENT '用户id',
  `permission_id` int(10) DEFAULT NULL COMMENT '权限id',
  `type` tinyint(4) DEFAULT NULL COMMENT '权限类型0减权限1增权限',
  PRIMARY KEY (`user_permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `system_user$permission` */

/*Table structure for table `system_user$role` */

DROP TABLE IF EXISTS `system_user$role`;

CREATE TABLE `system_user$role` (
  `user_role_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(10) DEFAULT NULL COMMENT '用户id',
  `role_id` int(10) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`user_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `system_user$role` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
