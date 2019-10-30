/*
Navicat MySQL Data Transfer

Source Server         : 10.40.40.161
Source Server Version : 50644
Source Host           : 10.40.40.161:3306
Source Database       : dcfront

Target Server Type    : MYSQL
Target Server Version : 50644
File Encoding         : 65001

Date: 2019-10-14 18:03:36
*/

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `dcfront` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;

USE `dcfront`;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `BUS_ATTACHMENT`
-- ----------------------------
DROP TABLE IF EXISTS `BUS_ATTACHMENT`;
CREATE TABLE `BUS_ATTACHMENT` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `FILE_NAME` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '原文件名',
  `ATT_NAME` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '新文件名',
  `TABLE_NAME` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表名',
  `FIELD_NAME` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字段名',
  `DATA_PRI_FIELD` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'guid' COMMENT '主键字段名称',
  `DATA_ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '数据id',
  `PLATFORM_CODE` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '平台编码',
  `FILE_PATH` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '日期文件路径',
  `UP_STATUS` int(2) NOT NULL DEFAULT '0' COMMENT '上传状态 0-未上传/1-已上传',
  PRIMARY KEY (`ID`),
  KEY `idx_att_name` (`ATT_NAME`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='附件表';

-- ----------------------------
-- Records of BUS_ATTACHMENT
-- ----------------------------
INSERT INTO `BUS_ATTACHMENT` VALUES ('1', 'rsa.jar', '1571046660782_93.jar', 'person', 'file', 'id', '1', '40313690', '2019/10', '1');

-- ----------------------------
-- Table structure for `BUS_ATTACHMENT_TMP`
-- ----------------------------
DROP TABLE IF EXISTS `BUS_ATTACHMENT_TMP`;
CREATE TABLE `BUS_ATTACHMENT_TMP` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `FILE_INDEX` int(11) NOT NULL DEFAULT '0' COMMENT '切分文件索引号',
  `START_POINT` bigint(20) NOT NULL DEFAULT '0' COMMENT '起始点',
  `ATT_NAME` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '新文件名',
  `FILE_NAME` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '原文件名',
  `PATH` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '日期文件路径',
  `FILE_LEN` bigint(20) NOT NULL DEFAULT '0' COMMENT '文件总长度',
  `UP_STATUS` int(2) NOT NULL DEFAULT '0' COMMENT '上传状态 0-未上传/1-已上传',
  PRIMARY KEY (`ID`),
  KEY `idx_att_name` (`ATT_NAME`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='附件临时表';

-- ----------------------------
-- Records of BUS_ATTACHMENT_TMP
-- ----------------------------
INSERT INTO `BUS_ATTACHMENT_TMP` VALUES ('1', '0', '0', '1571046660782_93.jar', 'rsa.jar', '2019/10', '27299761', '1');
INSERT INTO `BUS_ATTACHMENT_TMP` VALUES ('2', '1', '20971520', '1571046660782_93.jar', 'rsa.jar', '2019/10', '27299761', '1');

-- ----------------------------
-- Table structure for `BUS_CONFIG`
-- ----------------------------
DROP TABLE IF EXISTS `BUS_CONFIG`;
CREATE TABLE `BUS_CONFIG` (
  `ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `CFG_NAME` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '配置名称',
  `CODE` int(11) NOT NULL DEFAULT '0' COMMENT '枚举代码',
  `VI` bigint(20) NOT NULL DEFAULT '0' COMMENT '整形值',
  `VS` varchar(1000) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字符串值',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表';

-- ----------------------------
-- Records of BUS_CONFIG
-- ----------------------------
INSERT INTO `BUS_CONFIG` VALUES ('3584aca6-50b0-4253-bd98-69c825359c5b', '数据下载同步时间', '1000', '0', '');

-- ----------------------------
-- Table structure for `BUS_CUSTOM_ENTITY`
-- ----------------------------
DROP TABLE IF EXISTS `BUS_CUSTOM_ENTITY`;
CREATE TABLE `BUS_CUSTOM_ENTITY` (
  `ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `TABLE_NAME` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表名',
  `REAL_TAB_NAME` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '真实表名',
  `IN_CHINESE` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '中文名称',
  `CALLBACK_URL` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '回调地址',
  `DESCRIPTION` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '描述',
  `CREATE_AID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人id',
  `CREATE_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `UPDATE_AID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '修改人id',
  `UPDATE_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改时间',
  `RELEASE_AID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '发布人id',
  `RELEASE_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '发布时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uk_table_name` (`TABLE_NAME`) USING BTREE,
  UNIQUE KEY `uk_real_table_name` (`REAL_TAB_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='业务实体表';

-- ----------------------------
-- Records of BUS_CUSTOM_ENTITY
-- ----------------------------
INSERT INTO `BUS_CUSTOM_ENTITY` VALUES ('3e3ba9a7-76f2-4d13-8a7c-e32e77842447', 'person', 'TAB__PERSON', '用户表', '', '', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093574974', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1571033739665', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1571036704148');

-- ----------------------------
-- Table structure for `BUS_CUSTOM_FIELD`
-- ----------------------------
DROP TABLE IF EXISTS `BUS_CUSTOM_FIELD`;
CREATE TABLE `BUS_CUSTOM_FIELD` (
  `ID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '主键',
  `TABLE_NAME` varchar(150) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '表名',
  `FIELD_NAME` varchar(150) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '字段名称',
  `REAL_FIELD_NAME` varchar(150) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '真实字段名',
  `IN_CHINESE` varchar(200) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '中文名称',
  `FIELD_TYPE` int(2) NOT NULL DEFAULT '0' COMMENT '字段类型 0-整形/1-字符串/2-时间戳/3-浮点型',
  `FIELD_LENGTH` int(11) NOT NULL DEFAULT '11' COMMENT '字段长度',
  `PRECISIONS` int(11) NOT NULL DEFAULT '0' COMMENT '字段精度',
  `REQUIRED` int(2) NOT NULL DEFAULT '0' COMMENT '是否必填 0-否/1-是',
  `VALUE_FIELD` mediumtext COLLATE utf8_bin COMMENT '值域',
  `UNIQUE_KEY` int(2) NOT NULL DEFAULT '0' COMMENT '是否去重 0-否/1-是',
  `EXPOSED` int(1) NOT NULL DEFAULT '1' COMMENT '是否对外暴露 0-不暴露/1-暴露（对接系统是否访问）',
  `REMARKS` varchar(200) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '备注',
  `CREATE_AID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '创建人id',
  `CREATE_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `UPDATE_AID` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '修改人id',
  `UPDATE_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改时间',
  `RELEASE_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '发布时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uk_name_entity_id` (`TABLE_NAME`,`FIELD_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='实体字段记录表';

-- ----------------------------
-- Records of BUS_CUSTOM_FIELD
-- ----------------------------
INSERT INTO `BUS_CUSTOM_FIELD` VALUES ('3565125f-0828-49a6-88ac-cefacf962a64', 'person', 'age', 'COL__AGE', '年龄', '0', '11', '0', '1', '', '0', '1', '年龄', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093628953', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093628953', '1571036704148');
INSERT INTO `BUS_CUSTOM_FIELD` VALUES ('4a0898b1-2a62-4a5a-8092-510809f4c63b', 'person', 'id', 'COL__ID', '主键', '0', '11', '0', '1', '', '1', '1', '主键', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093601228', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093601228', '1571036704148');
INSERT INTO `BUS_CUSTOM_FIELD` VALUES ('764feb26-6be0-4422-8386-3971ea5bc8c5', 'person', 'SYS__PLATFORM_CODE', 'SYS__PLATFORM_CODE', '数据上传平台编码', '1', '50', '0', '1', '', '0', '1', '数据来源', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093575008', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093575008', '1571036704148');
INSERT INTO `BUS_CUSTOM_FIELD` VALUES ('8f74e054-0b8b-42ca-8842-fac7791a1a1c', 'person', 'file', 'COL__FILE', '附件', '1', '200', '0', '0', '', '0', '1', '附件', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1571033739660', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1571033739660', '1571036704148');
INSERT INTO `BUS_CUSTOM_FIELD` VALUES ('9088f9a6-a8d9-4d6f-abb4-f8188720aa0d', 'person', 'SYS__ID', 'SYS__ID', '主键', '1', '40', '0', '1', '', '0', '1', '主键字段', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093574986', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093574986', '1571036704148');
INSERT INTO `BUS_CUSTOM_FIELD` VALUES ('adcd7c0a-49b8-4693-85b3-192166f1fe9b', 'person', 'SYS__DATETIME_STAMP', 'SYS__DATETIME_STAMP', '数据入库时间', '1', '20', '0', '1', '', '0', '1', 'yyyyMMddHHmmss', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093575011', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093575011', '1571036704148');
INSERT INTO `BUS_CUSTOM_FIELD` VALUES ('d8e3e68c-c7ad-4952-9edd-9d25d45da901', 'person', 'name', 'COL__NAME', '用户名', '1', '40', '0', '1', '', '0', '1', '用户名', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093616265', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093616265', '1571036704148');
INSERT INTO `BUS_CUSTOM_FIELD` VALUES ('ecc2dfab-99bc-49a0-8373-379a2d580af2', 'person', 'SYS__CREATE_TIME', 'SYS__CREATE_TIME', '创建时间', '2', '20', '0', '1', '', '0', '1', '系统创建时间', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093574989', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093574989', '1571036704148');

-- ----------------------------
-- Table structure for `BUS_TABLE_PACK`
-- ----------------------------
DROP TABLE IF EXISTS `BUS_TABLE_PACK`;
CREATE TABLE `BUS_TABLE_PACK` (
  `ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `TABLE_NAME` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表名',
  `LAST_PACK_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '上次打包时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_TABLE_NAME` (`TABLE_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='表打包记录';

-- ----------------------------
-- Records of BUS_TABLE_PACK
-- ----------------------------
INSERT INTO `BUS_TABLE_PACK` VALUES ('0966e4b6-2dee-4ebf-a650-d9bc49ddd467', 'person', '0');

-- ----------------------------
-- Table structure for `DOWN_STOCK`
-- ----------------------------
DROP TABLE IF EXISTS `DOWN_STOCK`;
CREATE TABLE `DOWN_STOCK` (
  `ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `TABLE_NAME` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表名',
  `START_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '下载起始时间',
  `END_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '下载结束时间',
  `PAGE` int(11) NOT NULL DEFAULT '1' COMMENT '获取第几页数据',
  `PAGE_SIZE` int(11) NOT NULL DEFAULT '100' COMMENT '每页获取数量',
  `STATUS` int(2) NOT NULL DEFAULT '0' COMMENT '待传输状态 0-待下载/1-正在下载/2-下载已完成/3-下载失败',
  `CREATE_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '传输时间',
  PRIMARY KEY (`ID`),
  KEY `idx_create_time` (`CREATE_TIME`) USING BTREE,
  KEY `idx_table_name` (`TABLE_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='下载分步表';

-- ----------------------------
-- Records of DOWN_STOCK
-- ----------------------------

-- ----------------------------
-- Table structure for `STOCK`
-- ----------------------------
DROP TABLE IF EXISTS `STOCK`;
CREATE TABLE `STOCK` (
  `ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `PLATFORM_CODE` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '平台编码',
  `TABLE_NAME` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表名',
  `CONTENT` mediumtext COLLATE utf8mb4_unicode_ci COMMENT '带传输内容',
  `STATUS` int(2) NOT NULL DEFAULT '0' COMMENT '待传输状态 0-待传输/1-正在传输/2-传输成功/3-传输失败',
  `CREATE_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '传输时间',
  PRIMARY KEY (`ID`),
  KEY `idx_create_time` (`CREATE_TIME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据传输仓库表(待传输表)';

-- ----------------------------
-- Records of STOCK
-- ----------------------------

-- ----------------------------
-- Table structure for `TAB__PERSON`
-- ----------------------------
DROP TABLE IF EXISTS `TAB__PERSON`;
CREATE TABLE `TAB__PERSON` (
  `SYS__ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `COL__AGE` int(11) DEFAULT '0' COMMENT '年龄',
  `COL__ID` int(11) DEFAULT '0' COMMENT '主键',
  `SYS__PLATFORM_CODE` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '数据来源',
  `COL__FILE` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '附件',
  `SYS__DATETIME_STAMP` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'yyyyMMddHHmmss',
  `COL__NAME` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '用户名',
  `SYS__CREATE_TIME` bigint(20) DEFAULT '0' COMMENT '系统创建时间',
  `PACK_STATUS` int(2) DEFAULT '0' COMMENT '打包状态 0-未打包/1-已打包',
  PRIMARY KEY (`SYS__ID`),
  UNIQUE KEY `UK_42317A69` (`COL__ID`) USING BTREE,
  KEY `IDX_CREATE_TIME` (`SYS__CREATE_TIME`) USING BTREE,
  KEY `IDX_PLATFORM_CODE` (`SYS__PLATFORM_CODE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of TAB__PERSON
-- ----------------------------
INSERT INTO `TAB__PERSON` VALUES ('11', '1', '1', '40313690', '', '20180801121212', 'jack', '1533093709000', '0');
INSERT INTO `TAB__PERSON` VALUES ('602a4fe6-de9a-478e-903e-2846b8daa97f', '12', '12', '40313690', '', '20180801113702', 'jack 1111qion', '1533094622532', '0');
INSERT INTO `TAB__PERSON` VALUES ('7fa2c021-5e16-4ec7-9b0f-a5c8cf21ce57', '2', '2', '40313690', '', '20180801113254', 'jack qion', '1533094374153', '0');
INSERT INTO `TAB__PERSON` VALUES ('ca2c1ea7-2cfd-449a-9b40-fabc06273da1', '112', '112', '40313690', '', '20180801113702', '1tomcat', '1533094622531', '0');
