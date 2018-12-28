/*
 Navicat Premium Data Transfer

 Source Server         : 132.232.216.185
 Source Server Type    : MySQL
 Source Server Version : 50642
 Source Host           : 132.232.216.185
 Source Database       : system_web

 Target Server Type    : MySQL
 Target Server Version : 50642
 File Encoding         : utf-8

 Date: 11/21/2018 13:57:20 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `sys_depot`
-- ----------------------------
DROP TABLE IF EXISTS `sys_depot`;
CREATE TABLE `sys_depot` (
  `pk_depot_id` varchar(32) NOT NULL COMMENT '部门主键ID',
  `parent_depot_id` varchar(32) DEFAULT NULL COMMENT '父级部门ID',
  `depot_name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `depot_code` varchar(10) DEFAULT NULL COMMENT '部门编码',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_delete` smallint(1) DEFAULT NULL COMMENT '是否删除',
  `add_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `add_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`pk_depot_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理部门';

-- ----------------------------
--  Records of `sys_depot`
-- ----------------------------
BEGIN;
INSERT INTO `sys_depot` VALUES ('1', '0', 'IT部门', null, '0', '0', '1', '1', '1', '1'), ('2', '1', '产品部', null, '0', '0', null, null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `pk_log_id` varchar(32) NOT NULL COMMENT '日志主键',
  `fk_user_id` varchar(32) DEFAULT NULL COMMENT '操作人ID',
  `account` varchar(32) DEFAULT NULL COMMENT '操作人账号',
  `log_type` varchar(6) DEFAULT NULL COMMENT '日志类型',
  `operation` varchar(255) DEFAULT NULL COMMENT '操作事件',
  `operate_time` int(11) DEFAULT NULL COMMENT '操作时长',
  `class_name` varchar(255) DEFAULT NULL COMMENT '操作类',
  `params` varchar(2048) DEFAULT NULL COMMENT '参数',
  `ip` varchar(64) DEFAULT NULL COMMENT 'ip',
  `is_delete` smallint(1) DEFAULT NULL COMMENT '是否删除',
  `add_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `add_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`pk_log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='记录日志';

-- ----------------------------
--  Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `pk_menu_id` varchar(32) NOT NULL COMMENT '菜单主键',
  `parent_menu_id` varchar(32) DEFAULT NULL COMMENT '菜单名称',
  `menu_name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `menu_code` varchar(10) DEFAULT NULL,
  `menu_url` varchar(200) DEFAULT NULL COMMENT '菜单跳转地址',
  `menu_perms` varchar(200) DEFAULT NULL COMMENT '菜单权限',
  `menu_type` varchar(6) DEFAULT NULL COMMENT '菜单类型',
  `menu_icon` varchar(20) DEFAULT NULL COMMENT '菜单图标',
  `sort_num` int(11) DEFAULT NULL COMMENT '排序',
  `is_delete` smallint(1) DEFAULT NULL COMMENT '是否删除',
  `add_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `add_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`pk_menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
--  Records of `sys_menu`
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES ('0019cf24a670413795ce11111269e125', '0019cf24a670413795cef5cd4269e21d', '用户管理', 'SW0101', 'sys/user', 'sys:user:user', 'SW0101', 'user', '1', '0', '2c80802356e9a9da0156e9af1c010008', '2017-03-13 15:18:40', 'd70525d515a74c25ba1fa8bc892a85ee', '2018-11-20 14:30:59'), ('0019cf24a670413795cef5cd4269e123', '0019cf24a670413795cef5cd4269e213', '新增菜单', 'SW010001', 'sys/menu/add', 'sys:menu:add', 'SW0100', 'fa fa-th-list', '1', '0', '2c80802356e9a9da0156e9af1c010008', '2017-03-13 15:18:40', 'd70525d515a74c25ba1fa8bc892a85ee', '2018-11-19 16:58:10'), ('0019cf24a670413795cef5cd4269e124', '0019cf24a670413795cef5cd4269e213', '编辑菜单', 'SW010002', 'sys/menu/edit', 'sys:menu:edit', 'SW0100', 'fa fa-th-list', '2', '0', '2c80802356e9a9da0156e9af1c010008', '2017-03-13 15:18:40', '2c80802356e9a9da0156e9af1c010008', '2017-03-13 15:18:40'), ('0019cf24a670413795cef5cd4269e125', '0019cf24a670413795cef5cd4269e213', '编辑菜单', 'SW010003', 'sys/menu/delete', 'sys:menu:delete', 'SW0100', 'fa fa-th-list', '3', '0', '2c80802356e9a9da0156e9af1c010008', '2017-03-13 15:18:40', '2c80802356e9a9da0156e9af1c010008', '2017-03-13 15:18:40'), ('0019cf24a670413795cef5cd4269e213', '0019cf24a670413795cef5cd4269e21d', '菜单管理', 'SW0100', 'sys/menu', 'sys:menu:menu', 'SW0101', 'fa fa-th-list', '1', '0', '2c80802356e9a9da0156e9af1c010008', '2017-03-13 15:18:40', '2c80802356e9a9da0156e9af1c010008', '2017-03-13 15:18:40'), ('0019cf24a670413795cef5cd4269e21d', '0', '系统管理', 'SW01', 'sys', 'sys:sys', 'SW0101', 'fa fa-desktop', '1', '0', '2c80802356e9a9da0156e9af1c010008', '2017-03-13 15:18:40', '2c80802356e9a9da0156e9af1c010008', '2017-03-13 15:18:40'), ('56a53d1e09334a8fadb5953c624d3ec9', '0019cf24a670413795ce11111269e125', '新增用户', 'SW010100', '/sys/user/add', 'sys:user:add', 'SW0100', 'fa fa-th-list', '0', '0', 'd70525d515a74c25ba1fa8bc892a85ee', '2018-11-19 17:07:43', 'd70525d515a74c25ba1fa8bc892a85ee', '2018-11-19 17:07:43'), ('c71ed90b8e6a4b16bc0117a5eb0374b8', '0019cf24a670413795ce11111269e125', '删除用户', 'SW010102', '/sys/user/delete', 'sys:user:delete', 'SW0100', 'user', '2', '0', 'd70525d515a74c25ba1fa8bc892a85ee', '2018-11-20 16:43:03', 'd70525d515a74c25ba1fa8bc892a85ee', '2018-11-20 16:43:03'), ('edfea8d4a0fc45b7bd5c763468c5763c', '0019cf24a670413795ce11111269e125', '编辑用户', 'SW010101', 'sys/user/edit', 'sys:user:edit', 'SW0100', 'fa fa-th-list', '1', '0', 'd70525d515a74c25ba1fa8bc892a85ee', '2018-11-20 09:07:43', 'd70525d515a74c25ba1fa8bc892a85ee', '2018-11-20 09:07:43');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `pk_role_id` varchar(32) NOT NULL COMMENT '权限主键',
  `role_name` varchar(50) DEFAULT NULL COMMENT '权限名称',
  `role_sign` varchar(50) DEFAULT NULL COMMENT '权限标识',
  `remark` text COMMENT '备注',
  `is_delete` smallint(1) DEFAULT NULL COMMENT '是否删除',
  `add_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `add_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`pk_role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
--  Records of `sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('00092396e046431494c42ef8cc9a939a', '超级管理员角色', 'admin', '拥有最高权限', '0', '2c80802356e9a9da0156e9af1c010008', '2017-03-13 15:18:40', '2c80802356e9a9da0156e9af1c010008', '2017-03-13 15:18:40');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `pk_relation_id` varchar(32) NOT NULL COMMENT '关系主键',
  `fk_role_id` varchar(32) DEFAULT NULL COMMENT '权限ID',
  `fk_menu_id` varchar(32) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`pk_relation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限菜单关系';

-- ----------------------------
--  Records of `sys_role_menu`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES ('00092396e046431494c42ef8c123439a', '00092396e046431494c42ef8cc9a939a', 'edfea8d4a0fc45b7bd5c763468c5763c'), ('00092396e123431494c42ef8cc9a939a', '00092396e046431494c42ef8cc9a939a', 'c71ed90b8e6a4b16bc0117a5eb0374b8'), ('0019cf24a670413795cef12341234123', '00092396e046431494c42ef8cc9a939a', '0019cf24a670413795ce11111269e125'), ('0019cf24a670413795cef12341234124', '00092396e046431494c42ef8cc9a939a', '0019cf24a670413795cef5cd4269e123'), ('0019cf24a670413795cef12341234125', '00092396e046431494c42ef8cc9a939a', '0019cf24a670413795cef5cd4269e124'), ('0019cf24a670413795cef12341234126', '00092396e046431494c42ef8cc9a939a', '0019cf24a670413795cef5cd4269e125'), ('0019cf24a670413795cef1234123456d', '00092396e046431494c42ef8cc9a939a', '0019cf24a670413795cef5cd4269e21d'), ('0019cf24a670413795cef5cd41234561', '00092396e046431494c42ef8cc9a939a', '56a53d1e09334a8fadb5953c624d3ec9'), ('0019cf24a670413795cef5cd4123456d', '00092396e046431494c42ef8cc9a939a', '0019cf24a670413795cef5cd4269e213');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `pk_user_id` varchar(32) NOT NULL COMMENT '用户主键',
  `fk_depot_id` varchar(32) DEFAULT NULL COMMENT '组织ID',
  `user_name` varchar(50) DEFAULT NULL COMMENT '名称',
  `account` varchar(50) DEFAULT NULL COMMENT '账号',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `salt` varchar(45) DEFAULT NULL COMMENT '密码盐',
  `status` varchar(6) DEFAULT NULL,
  `phone` varchar(16) DEFAULT NULL COMMENT '电话',
  `email` varchar(20) DEFAULT NULL COMMENT '邮箱',
  `sex` varchar(6) DEFAULT NULL COMMENT '性别',
  `pic_id` varchar(32) DEFAULT NULL COMMENT '头像',
  `address` varchar(500) DEFAULT NULL COMMENT '地址',
  `province` varchar(255) DEFAULT NULL COMMENT '省份',
  `city` varchar(255) DEFAULT NULL COMMENT '城市',
  `area` varchar(255) DEFAULT NULL COMMENT '区划',
  `last_password_reset_date` datetime DEFAULT NULL,
  `is_delete` smallint(1) DEFAULT NULL COMMENT '是否删除',
  `add_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `add_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`pk_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('2c80802356e9a9da0156e9af1c010008', '1', '超级管理员', 'admin', '$2a$10$8nSvG5iQDmhhPzdCrAcRU.yBDYY7ck0eVfjqvHmETG/1227yJFvcG', '8pgby', 'SW0001', null, null, 'SW0201', null, null, null, null, null, null, '0', null, '2017-03-13 15:18:40', null, '2017-03-13 15:18:40'), ('d70525d515a74c25ba1fa8bc892a85ee', '2', 'yll', 'yll', '$2a$10$8nSvG5iQDmhhPzdCrAcRU.yBDYY7ck0eVfjqvHmETG/1227yJFvcG', null, 'SW0001', null, null, 'SW0201', null, null, null, null, null, '2018-05-25 16:34:00', '0', null, null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `pk_relation_id` varchar(32) NOT NULL COMMENT '关系主键',
  `fk_user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `fk_role_id` varchar(32) DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`pk_relation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户权限表';

-- ----------------------------
--  Records of `sys_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES ('8a9e48185b754539015b75793e1b000c', 'd70525d515a74c25ba1fa8bc892a85ee', '00092396e046431494c42ef8cc9a939a');
COMMIT;

-- ----------------------------
--  Table structure for `t_snu_customer`
-- ----------------------------
DROP TABLE IF EXISTS `t_snu_customer`;
CREATE TABLE `t_snu_customer` (
  `pk_customer_id` varchar(32) NOT NULL COMMENT '会员ID',
  `customer_name` varchar(50) DEFAULT NULL COMMENT '会员名称',
  `customer_account` varchar(50) DEFAULT NULL COMMENT '会员账号',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `salt` varchar(30) DEFAULT NULL COMMENT '密码盐',
  `status` varchar(6) DEFAULT NULL COMMENT '状态',
  `phone` varchar(16) DEFAULT NULL COMMENT '手机号',
  `email` varchar(20) DEFAULT NULL COMMENT '邮箱',
  `gender` varchar(6) DEFAULT NULL COMMENT '性别',
  `avatar_url` varchar(255) DEFAULT NULL COMMENT '头像',
  `country` varchar(20) DEFAULT NULL COMMENT '国家',
  `province` varchar(20) DEFAULT NULL COMMENT '省份',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `openid` varchar(50) DEFAULT NULL COMMENT '微信openid',
  `is_delete` smallint(1) DEFAULT NULL COMMENT '是否删除',
  `add_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `add_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`pk_customer_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='会员表';

-- ----------------------------
--  Records of `t_snu_customer`
-- ----------------------------
BEGIN;
INSERT INTO `t_snu_customer` VALUES ('4bb40c4d6993438c8843528c903563ee', null, null, '*_*allen', null, null, 'SW0001', null, null, '1', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK7rhABLVGfWZ2OKtz9qP4WUMozicTT4DTKgDmIS9X4EXbdLMCS9PoXzibTeaHEgzcCrKgHWKibZlL0Q/132', 'China', 'Zhejiang', 'Huzhou', null, 'ody7y5DC4W6RivFcyg_vtsffHtM0', '0', null, '2018-10-25 23:50:17', null, '2018-10-25 23:50:17'), ('b1cc088499af49d7a6eb69f65311437d', null, null, '*_*allen', null, null, 'SW0001', null, null, '1', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK7rhABLVGfWZ2OKtz9qP4WUMozicTT4DTKgDmIS9X4EXbdLMCS9PoXzibTeaHEgzcCrKgHWKibZlL0Q/132', 'China', 'Zhejiang', 'Huzhou', null, '0', '0', null, '2018-10-26 14:44:13', null, '2018-10-26 14:44:13');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
