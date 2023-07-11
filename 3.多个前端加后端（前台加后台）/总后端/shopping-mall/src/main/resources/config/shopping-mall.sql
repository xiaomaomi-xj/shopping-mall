/*
 Navicat Premium Data Transfer

 Source Server         : xiaomaomi-xj
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : localhost:3306
 Source Schema         : shopping-mall

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 26/03/2023 15:52:51
*/

-- 数据库--------------------------
-- shopping-mall
-- ---------------------------------
DROP DATABASE IF EXISTS `shopping-mall`;
CREATE DATABASE `shopping-mall` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `shopping-mall`;


-- 下面的就是执行的创建表结构


SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `admin_id` bigint(20) NOT NULL COMMENT '管理员用户id',
  `admin_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '管理员账号',
  `admin_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '管理员密码',
  PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for chat_boos
-- ----------------------------
DROP TABLE IF EXISTS `chat_boos`;
CREATE TABLE `chat_boos`  (
  `message_id` bigint(20) NOT NULL COMMENT '聊天消息id',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '对应的用户',
  `from_user` tinyint(1) NULL DEFAULT NULL COMMENT '发送人的身份',
  `message` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '发送的消息内容',
  `is_un_read` tinyint(1) NULL DEFAULT NULL COMMENT '是否已读',
  PRIMARY KEY (`message_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '聊天信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for container_module
-- ----------------------------
DROP TABLE IF EXISTS `container_module`;
CREATE TABLE `container_module`  (
  `container_module_id` bigint(20) NOT NULL COMMENT '主要展示商品的id',
  `title_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  PRIMARY KEY (`container_module_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '主要显示商品内容数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for extra_config
-- ----------------------------
DROP TABLE IF EXISTS `extra_config`;
CREATE TABLE `extra_config`  (
  `extra_id` bigint(20) NOT NULL COMMENT '额外的配置主键',
  `store_name_have_special` tinyint(1) NULL DEFAULT NULL COMMENT '商名中是否含有特殊字',
  `special_text` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '特殊字',
  `store_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商家名字',
  `password_template_text` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码模板',
  `below_page_text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '页脚内容',
  `register_bg_img_url` bigint(20) NULL DEFAULT NULL COMMENT '注册界面背景图',
  `login_bg_img_url` bigint(20) NULL DEFAULT NULL COMMENT '登录界面背景图',
  PRIMARY KEY (`extra_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '额外配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `goods_id` bigint(20) NOT NULL COMMENT '商品id',
  `goods_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名字',
  `goods_describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品描述',
  `goods_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品价格',
  `max_buy_num` int(11) NULL DEFAULT NULL COMMENT '库存数量',
  `toggle_view_goods` bigint(20) NULL DEFAULT NULL COMMENT '标题界面用到的多个商品，标题界面id',
  `rotation_chart_goods` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '轮播图用到的多个商品，轮播图id',
  `ad_goods` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '广告用到的多个商品，广告id',
  `container_special_goods` bigint(20) NULL DEFAULT NULL COMMENT '主要显示界面，左侧高的商品，页面id',
  `container_top_goods` bigint(20) NULL DEFAULT NULL COMMENT '主要显示界面，上排商品，页面id',
  `container_bottom_goods` bigint(20) NULL DEFAULT NULL COMMENT '主要显示界面，下排商品，页面id',
  PRIMARY KEY (`goods_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for img
-- ----------------------------
DROP TABLE IF EXISTS `img`;
CREATE TABLE `img`  (
  `img_id` bigint(20) NOT NULL COMMENT '图片id',
  `img_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片名字',
  `cover_goods` bigint(20) NULL DEFAULT NULL COMMENT '商品封面图片，商品id',
  `rotation_goods` bigint(20) NULL DEFAULT NULL COMMENT '商品详情图片，商品id',
  PRIMARY KEY (`img_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '图片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for merchant
-- ----------------------------
DROP TABLE IF EXISTS `merchant`;
CREATE TABLE `merchant`  (
  `merchant_id` bigint(20) NOT NULL COMMENT '商户主键',
  `merchant_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户号',
  `merchant_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户名字',
  `merchant_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户类型',
  `server_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '平台地址',
  `pay_platform_app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付平台应用id',
  `public_key` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商户公钥',
  `private_key` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商户私钥',
  `encrypt_password` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容加密密码',
  `callback_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回调地址',
  PRIMARY KEY (`merchant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商户信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_data
-- ----------------------------
DROP TABLE IF EXISTS `order_data`;
CREATE TABLE `order_data`  (
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `goods_id` bigint(20) NULL DEFAULT NULL COMMENT '对应的商品',
  `merchant_id` bigint(20) NULL DEFAULT NULL COMMENT '商户id',
  `goods_num` int(11) NULL DEFAULT NULL COMMENT '商品数量',
  `ship_status` int(1) NULL DEFAULT NULL COMMENT '运送状态',
  `total_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '总价格',
  `order_state` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单状态',
  `compose_order_id` bigint(20) NULL DEFAULT NULL COMMENT '购物车多个订单使用',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for shop_cart
-- ----------------------------
DROP TABLE IF EXISTS `shop_cart`;
CREATE TABLE `shop_cart`  (
  `shop_cart_id` bigint(20) NOT NULL COMMENT '购物车id',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  `goods_id` bigint(20) NULL DEFAULT NULL COMMENT '商品id',
  PRIMARY KEY (`shop_cart_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '购物车表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for toggle_view
-- ----------------------------
DROP TABLE IF EXISTS `toggle_view`;
CREATE TABLE `toggle_view`  (
  `toggle_view_id` bigint(20) NOT NULL COMMENT '搜索框左边的标题数据',
  `title_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题名字',
  PRIMARY KEY (`toggle_view_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '轮播图上方标题数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `user_head_url` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '用户头像数据',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户姓名',
  `user_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户账号',
  `user_sex` tinyint(1) NULL DEFAULT NULL COMMENT '用户性别',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户密码',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1637116823008747521 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for vip_card
-- ----------------------------
DROP TABLE IF EXISTS `vip_card`;
CREATE TABLE `vip_card`  (
  `vip_card_id` bigint(20) NOT NULL COMMENT '购物卡id',
  `vip_card_account` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购物卡号',
  `vip_card_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购物卡密码',
  `vip_card_balance` decimal(10, 2) NULL DEFAULT NULL COMMENT '购物卡余额',
  PRIMARY KEY (`vip_card_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '购物卡' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for wechat
-- ----------------------------
DROP TABLE IF EXISTS `wechat`;
CREATE TABLE `wechat`  (
  `wechat_id` bigint(20) NOT NULL COMMENT '微信登录账号id',
  `wechat_head_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像链接',
  `open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信账号标识',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信昵称',
  `sex` tinyint(1) NULL DEFAULT NULL COMMENT '微信性别',
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`wechat_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '微信登录账户' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
