/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50519
Source Host           : localhost:3306
Source Database       : hs_dev

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2017-07-15 14:47:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_function
-- ----------------------------
DROP TABLE IF EXISTS `sys_function`;
CREATE TABLE `sys_function` (
  `function_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单编号',
  `type_code` varchar(30) DEFAULT NULL COMMENT '菜单类型编码',
  `parent_function_id` int(11) DEFAULT NULL COMMENT '父菜单编号',
  `function_level` smallint(2) DEFAULT NULL COMMENT '菜单等级',
  `function_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `function_code` varchar(50) DEFAULT NULL COMMENT '菜单编码',
  `orderby` smallint(5) DEFAULT NULL COMMENT '菜单排序',
  `function_url` varchar(100) DEFAULT NULL COMMENT '菜单地址',
  `font_icon` varchar(32) DEFAULT NULL COMMENT '字体图标',
  `is_show` enum('y','n') DEFAULT NULL COMMENT '是否显示;y:是;n:否',
  `is_del` enum('y','n') DEFAULT NULL COMMENT '是否删除;y:是;n:否',
  `create_on` varchar(25) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`function_id`),
  KEY `FK_Reference_54` (`type_code`),
  KEY `FK_Reference_57` (`parent_function_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_function
-- ----------------------------
INSERT INTO `sys_function` VALUES ('1', 'admin', '0', '0', '首页', null, '0', '/', 'icon-plus', 'y', 'n', '2017-04-04 20:53:33');
INSERT INTO `sys_function` VALUES ('2', 'admin', '0', '0', '系统管理', null, '10', null, 'icon-plus', 'y', 'n', '2017-04-04 20:54:58');
INSERT INTO `sys_function` VALUES ('3', 'admin', '2', '1', '用户管理', null, '0', null, 'fa fa-user', 'y', 'n', '2017-04-04 21:01:00');
INSERT INTO `sys_function` VALUES ('6', 'admin', '3', '2', '后台用户', null, '0', '/user/list/admin', 'fa fa-user', 'y', 'n', '2017-04-04 23:02:42');
INSERT INTO `sys_function` VALUES ('7', 'admin', '2', '1', '角色管理', null, '0', null, 'fa fa-user-secret', 'y', 'n', '2017-04-05 09:53:10');
INSERT INTO `sys_function` VALUES ('8', 'admin', '7', '2', '后台角色', null, '0', '/role/list/admin', 'fa fa-user-secret', 'y', 'n', '2017-04-05 09:57:20');
INSERT INTO `sys_function` VALUES ('9', 'admin', '2', '1', '菜单管理', null, '0', null, 'fa fa-bars', 'y', 'n', '2017-04-05 09:59:48');
INSERT INTO `sys_function` VALUES ('10', 'admin', '9', '2', '菜单后台', null, '0', '/function/list/admin', 'fa fa-bars', 'y', 'n', '2017-04-05 10:00:05');

-- ----------------------------
-- Table structure for sys_function_operation
-- ----------------------------
DROP TABLE IF EXISTS `sys_function_operation`;
CREATE TABLE `sys_function_operation` (
  `operation_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `function_id` int(11) DEFAULT NULL COMMENT '功能id,关联t_s_function表id',
  `operation_code` varchar(50) DEFAULT NULL COMMENT '编码',
  `operation_icon` varchar(20) DEFAULT NULL COMMENT '图标',
  `operation_name` varchar(50) DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`operation_id`),
  KEY `FK_Reference_53` (`function_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='菜单操作表';

-- ----------------------------
-- Records of sys_function_operation
-- ----------------------------
INSERT INTO `sys_function_operation` VALUES ('1', '6', 'add', 'icon-bell-alt', '新增');
INSERT INTO `sys_function_operation` VALUES ('3', '6', 'update', 'icon-bolt', '编辑');
INSERT INTO `sys_function_operation` VALUES ('4', '6', 'delete', 'icon-bell', '删除');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type_code` varchar(20) DEFAULT NULL COMMENT '类型编码',
  `role_code` varchar(10) DEFAULT NULL COMMENT '编码',
  `role_name` varchar(100) NOT NULL COMMENT '名称',
  `operation` varchar(300) DEFAULT NULL COMMENT '操作',
  `orderby` int(3) DEFAULT NULL COMMENT '排序',
  `is_del` enum('y','n') DEFAULT NULL COMMENT '是否删除;y:是;n:否',
  `create_on` varchar(25) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'admin', 'admin', '管理员', '0,1', '0', 'n', '2017-03-31 17:57:45');

-- ----------------------------
-- Table structure for sys_role_function
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_function`;
CREATE TABLE `sys_role_function` (
  `role_function_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `function_id` int(11) DEFAULT NULL COMMENT '功能id,关联t_s_function表id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id,关联t_s_role表id',
  PRIMARY KEY (`role_function_id`),
  KEY `FK_Reference_51` (`role_id`),
  KEY `FK_Reference_52` (`function_id`)
) ENGINE=MyISAM AUTO_INCREMENT=80 DEFAULT CHARSET=utf8 COMMENT='角色功能';

-- ----------------------------
-- Records of sys_role_function
-- ----------------------------
INSERT INTO `sys_role_function` VALUES ('79', '10', '1');
INSERT INTO `sys_role_function` VALUES ('78', '9', '1');
INSERT INTO `sys_role_function` VALUES ('77', '8', '1');
INSERT INTO `sys_role_function` VALUES ('76', '7', '1');
INSERT INTO `sys_role_function` VALUES ('75', '6', '1');
INSERT INTO `sys_role_function` VALUES ('74', '3', '1');
INSERT INTO `sys_role_function` VALUES ('73', '2', '1');
INSERT INTO `sys_role_function` VALUES ('72', '1', '1');

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `role_user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色用户编号',
  `role_id` int(11) DEFAULT NULL COMMENT '主键id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户编号',
  PRIMARY KEY (`role_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COMMENT='角色用户关联表';

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `realname` varchar(30) DEFAULT NULL COMMENT '真实姓名',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `status` enum('0','1') DEFAULT NULL COMMENT '状态;0:正常;1:禁用',
  `is_del` enum('y','n') DEFAULT NULL COMMENT '是否删除;y:是;n:否',
  `orderno` smallint(3) DEFAULT NULL COMMENT '排序',
  `create_on` varchar(25) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '李名杰', null, '0', 'n', '0', '2017-04-01 14:34:54');
