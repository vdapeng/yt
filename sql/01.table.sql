/*
 Navicat Premium Data Transfer

 Source Server         : vdaoyun-mysql
 Source Server Type    : MySQL
 Source Server Version : 50559
 Source Host           : 39.106.107.185
 Source Database       : yt

 Target Server Type    : MySQL
 Target Server Version : 50559
 File Encoding         : utf-8

 Date: 06/08/2018 11:22:56 AM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sys_dict`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `dict_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典编号',
  `group_id` int(11) DEFAULT NULL COMMENT '所属分组',
  `code` varchar(50) DEFAULT NULL COMMENT '字典编码',
  `name` varchar(200) DEFAULT NULL COMMENT '字典名称',
  `states` enum('0','1') DEFAULT NULL COMMENT '状态;0:显示;1:隐藏',
  `orderby` int(3) DEFAULT NULL COMMENT '字典排序',
  PRIMARY KEY (`dict_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `sys_dict`
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` VALUES ('1', '1', 'man', '男', '0', '5'), ('2', '1', 'woman', '女', '0', '10'), ('11', '6', 'ds', '电商', '0', '1'), ('12', '6', 'it', 'IT科技', '0', '2'), ('13', '6', 'px', '培训', '0', '3'), ('14', '7', 'gr', '个人', '0', '1'), ('15', '7', 'qy', '企业', '0', '2'), ('16', '8', 'work', '作品', '0', '1'), ('17', '8', 'aboutus', '关于我们', '0', '2'), ('18', '8', 'contactus', '联系我们', '0', '3'), ('19', '8', 'feedback', '意见反馈', '0', '4'), ('21', '9', 'work_index', '首页', '0', '1'), ('23', '9', 'work_special', '专题', '0', '2'), ('24', '10', 'mirco_index', '首页', '0', '1'), ('25', '8', 'micro', '微课', '0', '5'), ('26', '11', 'labour', '劳务', '0', '1'), ('27', '11', 'earthwork', '土方', '0', '2');
COMMIT;

-- ----------------------------
--  Table structure for `sys_dict_group`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_group`;
CREATE TABLE `sys_dict_group` (
  `dict_group_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典组编号',
  `group_code` varchar(50) DEFAULT NULL COMMENT '字典组编码',
  `group_name` varchar(200) DEFAULT NULL COMMENT '字典组名称',
  `group_desc` varchar(200) DEFAULT NULL COMMENT '字典组描述',
  `orderby` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`dict_group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Records of `sys_dict_group`
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_group` VALUES ('1', 'sex', '性别', '11', '9'), ('2', 'city', '城市', '2', '8'), ('6', 'industry', '所属行业', null, '3'), ('7', 'customerType', '客户类型', null, '4'), ('8', 'cmstype', 'cms类型', null, '5'), ('9', 'adsensework', '广告位-作品', '作品模板的广告位', '6'), ('10', 'adsensemirco', '广告位-微课', null, '7'), ('11', 'clientProjectSubType', '分包方类型', null, '8');
COMMIT;

-- ----------------------------
--  Table structure for `sys_function`
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
) ENGINE=InnoDB AUTO_INCREMENT=249 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
--  Records of `sys_function`
-- ----------------------------
BEGIN;
INSERT INTO `sys_function` VALUES ('1', 'admin', '0', '0', '首页', null, '0', '/', 'fa fa-home', 'y', 'n', '2017-04-04 20:53:33'), ('2', 'admin', '0', '0', '系统管理', null, '20', null, 'fa fa-cog', 'y', 'n', '2017-04-04 20:54:58'), ('3', 'admin', '2', '1', '用户管理', null, '1', '/user/userList', '', 'y', 'n', '2017-04-04 21:01:00'), ('7', 'admin', '2', '1', '角色管理', null, '2', '/role/roleList/admin', '', 'y', 'n', '2017-04-05 09:53:10'), ('9', 'admin', '2', '1', '菜单管理', null, '3', '/function/functionList', '', 'y', 'n', '2017-04-05 09:59:48'), ('195', 'admin', '2', '1', '系统参数', null, '4', '/param/paramList', 'fa fa-address-book-o', 'y', 'n', '2017-09-08 09:51:45'), ('241', 'admin', '2', '1', '数据字典', null, '5', '/dict/dictGroupList', 'fa fa-plus', 'y', 'n', '2018-01-26 16:13:35');
COMMIT;

-- ----------------------------
--  Table structure for `sys_function_operation`
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
--  Table structure for `sys_param`
-- ----------------------------
DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param` (
  `param_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '参数编号',
  `name` varchar(20) DEFAULT NULL COMMENT '参数名称',
  `value` varchar(100) DEFAULT NULL COMMENT '参数值',
  `is_effect` enum('y','n') DEFAULT NULL COMMENT '是否生效;y:是;n:否',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `is_del` enum('y','n') DEFAULT NULL COMMENT '是否删除;y:是;n:否',
  `create_on` varchar(25) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`param_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统参数表';

-- ----------------------------
--  Table structure for `sys_role`
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
  `create_on` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
--  Records of `sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('1', 'admin', 'admin', '管理员', '0', '9', 'n', '2017-03-31 17:57:45'), ('2', 'admin', 'zhgd', '超级管理员', '0', '2', 'n', '2018-05-11 16:33:05'), ('3', 'admin', 'admin2', '超级管理员', null, '3', 'y', '2018-06-07 16:13:30');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role_function`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_function`;
CREATE TABLE `sys_role_function` (
  `role_function_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `function_id` int(11) DEFAULT NULL COMMENT '功能id,关联t_s_function表id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id,关联t_s_role表id',
  PRIMARY KEY (`role_function_id`),
  KEY `FK_Reference_51` (`role_id`),
  KEY `FK_Reference_52` (`function_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4251 DEFAULT CHARSET=utf8 COMMENT='角色功能';

-- ----------------------------
--  Records of `sys_role_function`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_function` VALUES ('4128', '1', '1'), ('4129', '215', '1'), ('4130', '218', '1'), ('4131', '219', '1'), ('4132', '221', '1'), ('4133', '222', '1'), ('4134', '223', '1'), ('4135', '224', '1'), ('4136', '225', '1'), ('4137', '226', '1'), ('4138', '227', '1'), ('4139', '228', '1'), ('4140', '229', '1'), ('4141', '230', '1'), ('4142', '231', '1'), ('4143', '232', '1'), ('4144', '233', '1'), ('4145', '234', '1'), ('4146', '235', '1'), ('4147', '236', '1'), ('4148', '237', '1'), ('4149', '244', '1'), ('4150', '238', '1'), ('4151', '239', '1'), ('4152', '240', '1'), ('4153', '246', '1'), ('4154', '245', '1'), ('4155', '2', '1'), ('4156', '3', '1'), ('4157', '7', '1'), ('4158', '9', '1'), ('4159', '195', '1'), ('4160', '241', '1'), ('4161', '247', '1'), ('4162', '248', '1'), ('4222', '1', '2'), ('4223', '215', '2'), ('4224', '218', '2'), ('4225', '219', '2'), ('4226', '221', '2'), ('4227', '222', '2'), ('4228', '223', '2'), ('4229', '224', '2'), ('4230', '225', '2'), ('4231', '226', '2'), ('4232', '227', '2'), ('4233', '228', '2'), ('4234', '229', '2'), ('4235', '230', '2'), ('4236', '231', '2'), ('4237', '232', '2'), ('4238', '233', '2'), ('4239', '234', '2'), ('4240', '235', '2'), ('4241', '236', '2'), ('4242', '237', '2'), ('4243', '238', '2'), ('4244', '239', '2'), ('4245', '240', '2'), ('4246', '246', '2'), ('4247', '245', '2'), ('4248', '2', '2'), ('4249', '247', '2'), ('4250', '248', '2');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `role_user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色用户编号',
  `role_id` int(11) DEFAULT NULL COMMENT '主键id',
  `user_id` int(11) DEFAULT NULL COMMENT '用户编号',
  PRIMARY KEY (`role_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='角色用户关联表';

-- ----------------------------
--  Records of `sys_role_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_user` VALUES ('1', '1', '1'), ('2', '2', '2'), ('3', '2', '3');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user`
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '李名杰', '17703664907', '0', 'n', '17', '2017-04-01 14:34:54'), ('2', 'zhgd', 'e10adc3949ba59abbe56e057f20f883e', '智慧工地', null, '0', 'n', '2', '2018-05-11 16:34:07'), ('3', 'test', 'e10adc3949ba59abbe56e057f20f883e', '李辉', '13007189377', '0', 'y', '3', '2018-06-07 15:52:21');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
