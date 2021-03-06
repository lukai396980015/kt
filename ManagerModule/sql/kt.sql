/*
Navicat MySQL Data Transfer

Source Server         : 本地mysql
Source Server Version : 50710
Source Host           : localhost:3306
Source Database       : kt

Target Server Type    : MYSQL
Target Server Version : 50710
File Encoding         : 65001

Date: 2018-07-15 20:39:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_permission`
-- ----------------------------
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `permission_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '权限名',
  `permission_url` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '权限对应路径',
  `permission_icon` varchar(256) COLLATE utf8_bin DEFAULT NULL COMMENT '图标',
  `permission_parent_id` int(11) DEFAULT NULL COMMENT '父id',
  `permission_order` int(11) DEFAULT NULL COMMENT '排序',
  `permission_status` varchar(8) COLLATE utf8_bin DEFAULT '' COMMENT '状态',
  `permission_createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `permission_last_updatetime` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='权限表';

-- ----------------------------
-- Records of t_permission
-- ----------------------------
INSERT INTO `t_permission` VALUES ('1', '用户管理', 'userManagerController/openUserManager', null, '0', '1', '1', '2018-07-09 11:24:02', '2018-07-09 11:24:10');
INSERT INTO `t_permission` VALUES ('2', '测试菜单', null, null, '0', '2', '1', '2018-07-09 11:24:00', '2018-07-09 11:24:08');
INSERT INTO `t_permission` VALUES ('3', '测试子菜单1', '456', null, '2', '1', '1', '2018-07-09 11:23:58', '2018-07-09 11:24:06');
INSERT INTO `t_permission` VALUES ('4', '测试子菜单2', '987', null, '2', '2', '1', '2018-07-09 11:23:55', '2018-07-09 11:24:04');
INSERT INTO `t_permission` VALUES ('5', '菜单管理', 'menuController/showAllMenu', null, '0', '1', '1', '2018-07-10 14:29:21', '2018-07-10 14:29:23');

-- ----------------------------
-- Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(128) COLLATE utf8_bin DEFAULT NULL COMMENT '角色id',
  `role_icon` varchar(256) COLLATE utf8_bin DEFAULT '' COMMENT '角色图标',
  `role_status` varchar(8) COLLATE utf8_bin DEFAULT NULL COMMENT '角色状态',
  `role_createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `role_last_updatetime` datetime DEFAULT NULL COMMENT '最后更新时间',
  `role_order` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色表';

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('1', '管理员', '', '1', '2018-07-09 09:58:57', '2018-07-09 09:59:00', '1');

-- ----------------------------
-- Table structure for `t_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
  `role_permission_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) DEFAULT NULL COMMENT '权限id',
  `role_permission_createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `role_permission_last_updatetime` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`role_permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色权限表';

-- ----------------------------
-- Records of t_role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_name` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `user_nick` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
  `user_phone` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '电话号码',
  `user_age` varchar(8) COLLATE utf8_bin DEFAULT NULL COMMENT '年龄',
  `user_sex` varbinary(8) DEFAULT NULL COMMENT '性别',
  `user_account` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '账号',
  `user_status` varchar(8) COLLATE utf8_bin DEFAULT NULL COMMENT '状态',
  `user_salt` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '随机',
  `user_password` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `user_email` varchar(64) COLLATE utf8_bin DEFAULT NULL COMMENT '邮件',
  `user_createtmie` datetime DEFAULT NULL COMMENT '创建时间',
  `user_last_updatetime` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户信息表';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'admin', '管理员', null, null, null, 'admin', null, null, '21232f297a57a5a743894a0e4a801fc3', null, null, null);

-- ----------------------------
-- Table structure for `t_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `user_role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `user_role_createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `user_role_last_updatetime` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`user_role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户角色表';

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('1', '1', '1', '2018-07-09 09:59:36', '2018-07-09 09:59:38');
