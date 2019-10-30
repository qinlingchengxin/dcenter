/*
Navicat MySQL Data Transfer

Source Server         : 10.40.40.161
Source Server Version : 50644
Source Host           : 10.40.40.161:3306
Source Database       : decenter

Target Server Type    : MYSQL
Target Server Version : 50644
File Encoding         : 65001

Date: 2019-10-14 18:03:40
*/

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `decenter` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;

USE `decenter`;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ADMIN`
-- ----------------------------
DROP TABLE IF EXISTS `ADMIN`;
CREATE TABLE `ADMIN` (
  `ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `MAG_TYPE` int(2) NOT NULL DEFAULT '0' COMMENT '类型 0-普通管理员/1-超级管理员',
  `USERNAME` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `PASSWORD` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员表';

-- ----------------------------
-- Records of ADMIN
-- ----------------------------
INSERT INTO `ADMIN` VALUES ('dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1', 'admin', '03b80bfa16faedc0a402a6205b75f436');

-- ----------------------------
-- Table structure for `BUS_ATTACHMENT`
-- ----------------------------
DROP TABLE IF EXISTS `BUS_ATTACHMENT`;
CREATE TABLE `BUS_ATTACHMENT` (
  `ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `PATH` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '日期路径',
  `ATT_NAME` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '文件名（后台生成）',
  `SRC_NAME` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '文件原名称',
  `PLATFORM_CODE` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '平台编码',
  `TABLE_NAME` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表名',
  `FIELD_NAME` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字段名',
  `DATA_PRI_FIELD` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'guid' COMMENT '主键字段名称',
  `DATA_ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '数据id',
  `UPDATE_STATUS` tinyint(1) NOT NULL DEFAULT '0' COMMENT '更新表状态',
  `CREATE_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '上传时间',
  PRIMARY KEY (`ID`),
  KEY `idx_plat_table` (`PLATFORM_CODE`,`TABLE_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='附件表';

-- ----------------------------
-- Records of BUS_ATTACHMENT
-- ----------------------------
INSERT INTO `BUS_ATTACHMENT` VALUES ('b6f1fcff-2134-459c-a498-9aba017483ae', '2019/10', '1571046660782_93.jar', 'rsa.jar', '40313690', 'person', 'file', 'id', '1', '1', '1571046962908');

-- ----------------------------
-- Table structure for `BUS_CONFIG`
-- ----------------------------
DROP TABLE IF EXISTS `BUS_CONFIG`;
CREATE TABLE `BUS_CONFIG` (
  `ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `CFG_NAME` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '配置名称',
  `CODE` int(11) NOT NULL DEFAULT '0' COMMENT '枚举代码',
  `VI` int(11) NOT NULL DEFAULT '0' COMMENT '整形值',
  `VS` varchar(1000) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字符串值',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表';

-- ----------------------------
-- Records of BUS_CONFIG
-- ----------------------------
INSERT INTO `BUS_CONFIG` VALUES ('3584aca6-50b0-4253-bd99-69c825359c5b', '上传数据条数限制', '1000', '1000', '');

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
INSERT INTO `BUS_CUSTOM_ENTITY` VALUES ('3e3ba9a7-76f2-4d13-8a7c-e32e77842447', 'person', 'TAB__PERSON', '用户表', '', '', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093574974', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1571033739665', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1571033749812');

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
INSERT INTO `BUS_CUSTOM_FIELD` VALUES ('3565125f-0828-49a6-88ac-cefacf962a64', 'person', 'age', 'COL__AGE', '年龄', '0', '11', '0', '1', '', '0', '1', '年龄', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093628953', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093628953', '1533093644462');
INSERT INTO `BUS_CUSTOM_FIELD` VALUES ('4a0898b1-2a62-4a5a-8092-510809f4c63b', 'person', 'id', 'COL__ID', '主键', '0', '11', '0', '1', '', '1', '1', '主键', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093601228', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093601228', '1533093644462');
INSERT INTO `BUS_CUSTOM_FIELD` VALUES ('764feb26-6be0-4422-8386-3971ea5bc8c5', 'person', 'SYS__PLATFORM_CODE', 'SYS__PLATFORM_CODE', '数据上传平台编码', '1', '50', '0', '1', '', '0', '1', '数据来源', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093575008', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093575008', '1533093644462');
INSERT INTO `BUS_CUSTOM_FIELD` VALUES ('8f74e054-0b8b-42ca-8842-fac7791a1a1c', 'person', 'file', 'COL__FILE', '附件', '1', '200', '0', '0', '', '0', '1', '附件', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1571033739660', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1571033739660', '1571033749812');
INSERT INTO `BUS_CUSTOM_FIELD` VALUES ('9088f9a6-a8d9-4d6f-abb4-f8188720aa0d', 'person', 'SYS__ID', 'SYS__ID', '主键', '1', '40', '0', '1', '', '0', '1', '主键字段', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093574986', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093574986', '1533093644462');
INSERT INTO `BUS_CUSTOM_FIELD` VALUES ('adcd7c0a-49b8-4693-85b3-192166f1fe9b', 'person', 'SYS__DATETIME_STAMP', 'SYS__DATETIME_STAMP', '数据入库时间', '1', '20', '0', '1', '', '0', '1', 'yyyyMMddHHmmss', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093575011', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093575011', '1533093644462');
INSERT INTO `BUS_CUSTOM_FIELD` VALUES ('d8e3e68c-c7ad-4952-9edd-9d25d45da901', 'person', 'name', 'COL__NAME', '用户名', '1', '40', '0', '1', '', '0', '1', '用户名', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093616265', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093616265', '1533093644462');
INSERT INTO `BUS_CUSTOM_FIELD` VALUES ('ecc2dfab-99bc-49a0-8373-379a2d580af2', 'person', 'SYS__CREATE_TIME', 'SYS__CREATE_TIME', '创建时间', '2', '20', '0', '1', '', '0', '1', '系统创建时间', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093574989', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093574989', '1533093644462');

-- ----------------------------
-- Table structure for `BUS_DATA_TRANS_LOG`
-- ----------------------------
DROP TABLE IF EXISTS `BUS_DATA_TRANS_LOG`;
CREATE TABLE `BUS_DATA_TRANS_LOG` (
  `ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `PLATFORM_CODE` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '平台编码',
  `TABLE_NAME` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户表',
  `TRANS_TYPE` int(2) NOT NULL DEFAULT '0' COMMENT '传输类型 0-上行/1-下行',
  `CONTENT` mediumtext COLLATE utf8mb4_unicode_ci COMMENT '传输内容',
  `FAILED_CONTENT` mediumtext COLLATE utf8mb4_unicode_ci COMMENT '无效数据',
  `AMOUNT` int(11) NOT NULL DEFAULT '0' COMMENT '成功条数',
  `STATUS` int(2) NOT NULL DEFAULT '1' COMMENT '传输状态 0-失败/1-成功',
  `CREATE_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '上传或下载时间',
  `REMARKS` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据传输日志';

-- ----------------------------
-- Records of BUS_DATA_TRANS_LOG
-- ----------------------------
INSERT INTO `BUS_DATA_TRANS_LOG` VALUES ('467b12e8-47a8-476a-895b-6de06ce26109', '40313690', 'person', '0', '[{\"age\":112,\"id\":112,\"name\":\"1tomcat\"},{\"age\":12,\"id\":12,\"name\":\"jack 1111qion\"}]', '[]', '2', '1', '1533094622559', 'success:2 failed:0');
INSERT INTO `BUS_DATA_TRANS_LOG` VALUES ('55b7a525-b745-4601-8475-584736311410', '40313690', 'person', '0', 'uTtMdZU0V0jFXZeYvEheJ2EemRn9kIuTh1CcinjuhPjKBsXZs4U6TnYUvFKqAydkSLdtjSX4Ipig3Vy+c3cTXCf5d6CkAFtUbubZnhxfib0NGrwfOwJ8ePhPG8ZcqlrQjFw7F6GauZaORFJ1H+gC+sFqTjnkeRhRPngPloTazEo=', '[]', '2', '1', '1533094374190', 'success:2 failed:0');

-- ----------------------------
-- Table structure for `BUS_ENUM`
-- ----------------------------
DROP TABLE IF EXISTS `BUS_ENUM`;
CREATE TABLE `BUS_ENUM` (
  `ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `CODE` int(11) NOT NULL DEFAULT '0' COMMENT '枚举code值',
  `ENUM_NAME` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '枚举名称',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统枚举表';

-- ----------------------------
-- Records of BUS_ENUM
-- ----------------------------
INSERT INTO `BUS_ENUM` VALUES ('1', '1000', '地区');

-- ----------------------------
-- Table structure for `BUS_ENUM_VALUE`
-- ----------------------------
DROP TABLE IF EXISTS `BUS_ENUM_VALUE`;
CREATE TABLE `BUS_ENUM_VALUE` (
  `ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `ENUM_CODE` int(11) NOT NULL DEFAULT '0' COMMENT '枚举CODE',
  `VI` int(11) NOT NULL DEFAULT '0' COMMENT '整形值',
  `VS` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字符串值',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统枚举值表';

-- ----------------------------
-- Records of BUS_ENUM_VALUE
-- ----------------------------
INSERT INTO `BUS_ENUM_VALUE` VALUES ('38332d01-4691-4618-b865-ab6c2b9d41dc', '1000', '0', '云南');
INSERT INTO `BUS_ENUM_VALUE` VALUES ('38332d01-4691-4618-b865-ab6c2b9d51d4', '1000', '0', '北京');
INSERT INTO `BUS_ENUM_VALUE` VALUES ('38332d01-4691-4618-b865-ab6c2b9d51dc', '1000', '0', '河南');
INSERT INTO `BUS_ENUM_VALUE` VALUES ('794152f0-afd2-4bbb-ab58-731c85018d78', '1000', '0', '山西');
INSERT INTO `BUS_ENUM_VALUE` VALUES ('97637332-5643-4fbc-8321-4565d64e3b30', '1000', '0', '甘肃');
INSERT INTO `BUS_ENUM_VALUE` VALUES ('9765817a-7cc8-4089-bc60-00a6e9919d93', '1000', '0', '内蒙古');
INSERT INTO `BUS_ENUM_VALUE` VALUES ('cdc99e7a-6557-4700-b8f4-ea6d87017541', '1000', '0', '山东');

-- ----------------------------
-- Table structure for `BUS_MONITOR`
-- ----------------------------
DROP TABLE IF EXISTS `BUS_MONITOR`;
CREATE TABLE `BUS_MONITOR` (
  `ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `PLATFORM_CODE` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '平台编码',
  `TYPE` int(2) NOT NULL DEFAULT '0' COMMENT '监控方式 0-ping/1-telnet',
  `IP` varchar(15) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '监控ip',
  `PORT` int(11) NOT NULL DEFAULT '0' COMMENT '监控端口',
  `INTERVAL_DAY` int(11) NOT NULL DEFAULT '0' COMMENT '间隔天',
  `INTERVAL_HOUR` int(11) NOT NULL DEFAULT '0' COMMENT '间隔小时',
  `INTERVAL_MINUTE` int(11) NOT NULL DEFAULT '0' COMMENT '间隔分钟',
  `NOTICE` int(2) NOT NULL DEFAULT '0' COMMENT '是否通知 0-否/1-是',
  `UP_AMOUNT` int(11) NOT NULL DEFAULT '0' COMMENT '上传量',
  `DOWN_AMOUNT` int(11) NOT NULL DEFAULT '0' COMMENT '下载量',
  `STATUS` int(2) NOT NULL DEFAULT '0' COMMENT '监控状态 0-待检测/1-正常/2-挂掉',
  `CREATE_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统监控';

-- ----------------------------
-- Records of BUS_MONITOR
-- ----------------------------

-- ----------------------------
-- Table structure for `BUS_MONITOR_CONTACT`
-- ----------------------------
DROP TABLE IF EXISTS `BUS_MONITOR_CONTACT`;
CREATE TABLE `BUS_MONITOR_CONTACT` (
  `ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `M_ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '监控id',
  `NAME` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '通知人员姓名',
  `TEL_NO` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '通知人手机号 ',
  `EMAIL` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '通知人邮箱地址',
  `CREATE_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='监控通知联系人';

-- ----------------------------
-- Records of BUS_MONITOR_CONTACT
-- ----------------------------

-- ----------------------------
-- Table structure for `BUS_PLATFORM`
-- ----------------------------
DROP TABLE IF EXISTS `BUS_PLATFORM`;
CREATE TABLE `BUS_PLATFORM` (
  `ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `CODE` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '平台编码',
  `PLAT_NAME` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '平台名称',
  `AREA_CODE` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '地区代码',
  `CONTACT_USERNAME` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '联系人',
  `CONTACT_PHONE` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '联系电话',
  `TRANS_TYPE` int(2) NOT NULL DEFAULT '2' COMMENT '对接方式 0-共享库/1-共享目录/2-接口',
  `SHARE_PATH` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '共享目录',
  `PUBLIC_KEY` varchar(2000) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '公钥',
  `PRIVATE_KEY` varchar(2000) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '私钥',
  `CREATE_AID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人id',
  `CREATE_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `UPDATE_AID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '修改人id',
  `UPDATE_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uk_code` (`CODE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='平台（接入系统）';

-- ----------------------------
-- Records of BUS_PLATFORM
-- ----------------------------
INSERT INTO `BUS_PLATFORM` VALUES ('bffa1d58-6032-433a-8ace-e05f02939bf1', '40313690', '内蒙古监督平台', '9765817a-7cc8-4089-bc60-00a6e9919d93', 'xxx', '18888888888', '2', '/data', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZOR3AArr4CW6SqjOe0hrS+Kj3ICwbQOyaAJnRniyJLcnmiaVJp8CdZyl+uioKXxKGdDasFN9xUWm2fT2cnEn1xjYMRb1tMgfH1RO/S6HMe8bvXCDwiKWnRUvI1+8v70OMFN6QpaOAvYe0gYRFO3/yWpUXji6gENwVq2HCNUW+SwIDAQAB', 'MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANk5HcACuvgJbpKqM57SGtL4qPcgLBtA7JoAmdGeLIktyeaJpUmnwJ1nKX66KgpfEoZ0NqwU33FRabZ9PZycSfXGNgxFvW0yB8fVE79Locx7xu9cIPCIpadFS8jX7y/vQ4wU3pClo4C9h7SBhEU7f/JalReOLqAQ3BWrYcI1Rb5LAgMBAAECgYEAt2v2RgtIK2dzed7EReiuA9U6f6on9D9nSkcVm54Phyol4Uf7TO2cxIy2yi6Sjz1mXChHfhkF8B7Jv/GTE+oYvCQ3ysb73PtoAX9gUvHzXFdcaAkTJ4KumzHvi4/v/oh32ADQhkT6aE47loaLm7ihWhVqpthYiyd6f04Oe9y/zoECQQD1e6ssqsJDpcG5MLPfLTEzk9My090dV0kkvRXNDCYmflfOHDSCIHi+vqE7kTNWtEb9qgZdX8ISMLJNgH1WNmgLAkEA4oeBv+AcJb9QGI6V7i/AWNrCnO2JTOv7VyL08Cjibt7zVUcj+fLMqwW6crvGETzPQLbr2p0x9QoppSipt76qwQJAX42h1aMqNvbAb3t7qHWI31C9Nt2tBgzB1eEcKVJ/TyCCwSHNdpF5LpY5YFxmpWtK7tZN3+60NixqWyyWnQZUfQJBAODO194iu+xeG0/zmSmDLCFCIKb6OSskeg+0ul3oRScAjX5RFowEAEetbKZWYS9UKN4xaolPPXfP+RpV63BVgUECQQCLYu4GKxXfqB7CNDkRiK0kFl7KqvQLdUfBE8k/+lRtsPPjbpxIpjqQ+0WIYWR0GqHhD8og2gb2xl6dt+GAcphY', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1513045441601', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1521683539919');
INSERT INTO `BUS_PLATFORM` VALUES ('c91764a5-7370-4ebe-b08d-9c650f22068a', '35dc4835', '山东公共服务平台', 'cdc99e7a-6557-4700-b8f4-ea6d87017541', 'xxx', '188888888', '2', '/data', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZOR3AArr4CW6SqjOe0hrS+Kj3ICwbQOyaAJnRniyJLcnmiaVJp8CdZyl+uioKXxKGdDasFN9xUWm2fT2cnEn1xjYMRb1tMgfH1RO/S6HMe8bvXCDwiKWnRUvI1+8v70OMFN6QpaOAvYe0gYRFO3/yWpUXji6gENwVq2HCNUW+SwIDAQAB', 'MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANk5HcACuvgJbpKqM57SGtL4qPcgLBtA7JoAmdGeLIktyeaJpUmnwJ1nKX66KgpfEoZ0NqwU33FRabZ9PZycSfXGNgxFvW0yB8fVE79Locx7xu9cIPCIpadFS8jX7y/vQ4wU3pClo4C9h7SBhEU7f/JalReOLqAQ3BWrYcI1Rb5LAgMBAAECgYEAt2v2RgtIK2dzed7EReiuA9U6f6on9D9nSkcVm54Phyol4Uf7TO2cxIy2yi6Sjz1mXChHfhkF8B7Jv/GTE+oYvCQ3ysb73PtoAX9gUvHzXFdcaAkTJ4KumzHvi4/v/oh32ADQhkT6aE47loaLm7ihWhVqpthYiyd6f04Oe9y/zoECQQD1e6ssqsJDpcG5MLPfLTEzk9My090dV0kkvRXNDCYmflfOHDSCIHi+vqE7kTNWtEb9qgZdX8ISMLJNgH1WNmgLAkEA4oeBv+AcJb9QGI6V7i/AWNrCnO2JTOv7VyL08Cjibt7zVUcj+fLMqwW6crvGETzPQLbr2p0x9QoppSipt76qwQJAX42h1aMqNvbAb3t7qHWI31C9Nt2tBgzB1eEcKVJ/TyCCwSHNdpF5LpY5YFxmpWtK7tZN3+60NixqWyyWnQZUfQJBAODO194iu+xeG0/zmSmDLCFCIKb6OSskeg+0ul3oRScAjX5RFowEAEetbKZWYS9UKN4xaolPPXfP+RpV63BVgUECQQCLYu4GKxXfqB7CNDkRiK0kFl7KqvQLdUfBE8k/+lRtsPPjbpxIpjqQ+0WIYWR0GqHhD8og2gb2xl6dt+GAcphY', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1513050407919', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1521683546026');
INSERT INTO `BUS_PLATFORM` VALUES ('e6ad5676-7cf4-4699-9d92-e497a2d59dc4', '6c46ab89', '云南公共服务平台', '38332d01-4691-4618-b865-ab6c2b9d41dc', 'xxx', '18888888888', '2', '/data', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZOR3AArr4CW6SqjOe0hrS+Kj3ICwbQOyaAJnRniyJLcnmiaVJp8CdZyl+uioKXxKGdDasFN9xUWm2fT2cnEn1xjYMRb1tMgfH1RO/S6HMe8bvXCDwiKWnRUvI1+8v70OMFN6QpaOAvYe0gYRFO3/yWpUXji6gENwVq2HCNUW+SwIDAQAB', 'MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANk5HcACuvgJbpKqM57SGtL4qPcgLBtA7JoAmdGeLIktyeaJpUmnwJ1nKX66KgpfEoZ0NqwU33FRabZ9PZycSfXGNgxFvW0yB8fVE79Locx7xu9cIPCIpadFS8jX7y/vQ4wU3pClo4C9h7SBhEU7f/JalReOLqAQ3BWrYcI1Rb5LAgMBAAECgYEAt2v2RgtIK2dzed7EReiuA9U6f6on9D9nSkcVm54Phyol4Uf7TO2cxIy2yi6Sjz1mXChHfhkF8B7Jv/GTE+oYvCQ3ysb73PtoAX9gUvHzXFdcaAkTJ4KumzHvi4/v/oh32ADQhkT6aE47loaLm7ihWhVqpthYiyd6f04Oe9y/zoECQQD1e6ssqsJDpcG5MLPfLTEzk9My090dV0kkvRXNDCYmflfOHDSCIHi+vqE7kTNWtEb9qgZdX8ISMLJNgH1WNmgLAkEA4oeBv+AcJb9QGI6V7i/AWNrCnO2JTOv7VyL08Cjibt7zVUcj+fLMqwW6crvGETzPQLbr2p0x9QoppSipt76qwQJAX42h1aMqNvbAb3t7qHWI31C9Nt2tBgzB1eEcKVJ/TyCCwSHNdpF5LpY5YFxmpWtK7tZN3+60NixqWyyWnQZUfQJBAODO194iu+xeG0/zmSmDLCFCIKb6OSskeg+0ul3oRScAjX5RFowEAEetbKZWYS9UKN4xaolPPXfP+RpV63BVgUECQQCLYu4GKxXfqB7CNDkRiK0kFl7KqvQLdUfBE8k/+lRtsPPjbpxIpjqQ+0WIYWR0GqHhD8og2gb2xl6dt+GAcphY', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1513045678008', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1513045678008');

-- ----------------------------
-- Table structure for `BUS_SYNC_TABLE`
-- ----------------------------
DROP TABLE IF EXISTS `BUS_SYNC_TABLE`;
CREATE TABLE `BUS_SYNC_TABLE` (
  `ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `PLATFORM_CODE` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '平台编号',
  `TABLE_NAME` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表名',
  `RELEASE_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '发布时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='前置机同步表';

-- ----------------------------
-- Records of BUS_SYNC_TABLE
-- ----------------------------
INSERT INTO `BUS_SYNC_TABLE` VALUES ('fd7675e4-880b-40a4-b027-3d78edf6dec1', '40313690', 'person', '1571033749812');

-- ----------------------------
-- Table structure for `BUS_TF_CHANGE_LOG`
-- ----------------------------
DROP TABLE IF EXISTS `BUS_TF_CHANGE_LOG`;
CREATE TABLE `BUS_TF_CHANGE_LOG` (
  `ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `CHG_TYPE` int(2) NOT NULL DEFAULT '0' COMMENT '修改类型 0-实体/1-字段',
  `OBJ_ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '修改对象id',
  `UPDATE_AID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '修改人id',
  `UPDATE_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改时间',
  `CONTENT` varchar(2000) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '修改说明',
  PRIMARY KEY (`ID`),
  KEY `idx_obj_id` (`OBJ_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='实体或字段修改记录表';

-- ----------------------------
-- Records of BUS_TF_CHANGE_LOG
-- ----------------------------

-- ----------------------------
-- Table structure for `BUS_USER`
-- ----------------------------
DROP TABLE IF EXISTS `BUS_USER`;
CREATE TABLE `BUS_USER` (
  `ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `USERNAME` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `PASSWORD` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户密码',
  `PLATFORM_CODE` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '平台编码',
  `CREATE_AID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建人id',
  `CREATE_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `UPDATE_AID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '修改人id',
  `UPDATE_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ----------------------------
-- Records of BUS_USER
-- ----------------------------
INSERT INTO `BUS_USER` VALUES ('77b8609c-c1a6-474b-ab77-baf6558f9c7a', 'zhaili', '03b80bfa16faedc0a402a6205b75f436', '40313690', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1513061716463', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1513061726795');

-- ----------------------------
-- Table structure for `ENTITY_PRIVILEGE`
-- ----------------------------
DROP TABLE IF EXISTS `ENTITY_PRIVILEGE`;
CREATE TABLE `ENTITY_PRIVILEGE` (
  `ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `PLATFORM_CODE` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '平台编码',
  `TABLE_NAME` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户表',
  `PRI_TYPE` int(2) NOT NULL DEFAULT '0' COMMENT '权限类型 0-无权/1-上行/2-下行',
  `ASSIGNER_AID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '权限分配人id',
  `ASSIGNER_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '分配时间',
  PRIMARY KEY (`ID`),
  KEY `idx_plat_ent_id` (`PLATFORM_CODE`,`TABLE_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='实体访问权限';

-- ----------------------------
-- Records of ENTITY_PRIVILEGE
-- ----------------------------
INSERT INTO `ENTITY_PRIVILEGE` VALUES ('09186d38-1b74-4aed-9d31-3a2ce8c1b842', '40313690', 'person', '1', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093655338');
INSERT INTO `ENTITY_PRIVILEGE` VALUES ('6b8a183d-9331-4c64-82a8-822bd635787b', '40313690', 'person', '2', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093661543');

-- ----------------------------
-- Table structure for `EP_CHANGE_LOG`
-- ----------------------------
DROP TABLE IF EXISTS `EP_CHANGE_LOG`;
CREATE TABLE `EP_CHANGE_LOG` (
  `ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `PLATFORM_CODE` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '平台编码',
  `TABLE_NAME` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表名',
  `CHG_TYPE` int(2) NOT NULL DEFAULT '0' COMMENT '修改类型 0-新增/1-取消',
  `UPDATE_AID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '修改人id',
  `UPDATE_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  KEY `idx_obj_id` (`TABLE_NAME`) USING BTREE,
  KEY `idx_plat_code` (`PLATFORM_CODE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='实体权限修改记录表';

-- ----------------------------
-- Records of EP_CHANGE_LOG
-- ----------------------------
INSERT INTO `EP_CHANGE_LOG` VALUES ('4f9398de-d332-4117-b1ba-24365a054ac7', '40313690', 'person', '0', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093655341');
INSERT INTO `EP_CHANGE_LOG` VALUES ('c4d455e4-ba91-4b90-b990-8d38462f0dcb', '40313690', 'person', '0', 'dfde0f72-af48-40a9-8c27-2dd06627ee8b', '1533093661545');

-- ----------------------------
-- Table structure for `ETL_ALL_FIELD`
-- ----------------------------
DROP TABLE IF EXISTS `ETL_ALL_FIELD`;
CREATE TABLE `ETL_ALL_FIELD` (
  `id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '主键',
  `table_id` varchar(40) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '表名id',
  `name` varchar(150) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '字段名称',
  `pri_key` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否为主键字段 0-否/1-是',
  `comment` varchar(200) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '注释',
  `create_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name_entity_id` (`table_id`,`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='etl数据源对应的所有字段';

-- ----------------------------
-- Records of ETL_ALL_FIELD
-- ----------------------------

-- ----------------------------
-- Table structure for `ETL_ALL_TABLE`
-- ----------------------------
DROP TABLE IF EXISTS `ETL_ALL_TABLE`;
CREATE TABLE `ETL_ALL_TABLE` (
  `id` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `ds_id` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '数据源id',
  `name` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表名',
  `comment` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表注释',
  `create_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_table_name` (`name`,`ds_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='etl数据源对应的所有数据表';

-- ----------------------------
-- Records of ETL_ALL_TABLE
-- ----------------------------

-- ----------------------------
-- Table structure for `ETL_DATA_SOURCE`
-- ----------------------------
DROP TABLE IF EXISTS `ETL_DATA_SOURCE`;
CREATE TABLE `ETL_DATA_SOURCE` (
  `ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `SOURCE_NAME` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '数据源名称，唯一',
  `DB_TYPE` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '数据库类型Mysql、Oracle、MSSQL',
  `DB_IP` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '数据库ip',
  `DB_PORT` int(11) NOT NULL DEFAULT '0' COMMENT '数据库端口',
  `DB_NAME` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '数据库名称',
  `DB_USER_NAME` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '数据库用户名',
  `DB_PWD` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '数据库用户密码',
  `CREATE_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uk_sn` (`SOURCE_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='ETL数据源';

-- ----------------------------
-- Records of ETL_DATA_SOURCE
-- ----------------------------

-- ----------------------------
-- Table structure for `ETL_ENTITY`
-- ----------------------------
DROP TABLE IF EXISTS `ETL_ENTITY`;
CREATE TABLE `ETL_ENTITY` (
  `ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `PRJ_ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '项目id',
  `SRC_TAB_NAME` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '原表名',
  `DES_TAB_NAME` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '目标表名',
  `SRC_PRIMARY_KEY` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '原表主键字段',
  `DES_PRIMARY_KEY` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '目标表主键字段',
  `DESCRIPTION` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '描述',
  `ETL_ID` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '作业名称',
  `CREATE_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  `REPEAT` int(2) NOT NULL DEFAULT '0' COMMENT '是否重复 0-否/1-是',
  `SCHEDULE_TYPE` int(2) NOT NULL DEFAULT '0' COMMENT '定时类型  1-间隔/2-天/3-周/4-月',
  `INTERVAL_SECOND` int(11) NOT NULL DEFAULT '0' COMMENT '间隔秒',
  `INTERVAL_MINUTE` int(11) NOT NULL DEFAULT '30' COMMENT '间隔分',
  `FIXED_HOUR` int(11) NOT NULL DEFAULT '12' COMMENT '固定时',
  `FIXED_MINUTE` int(11) NOT NULL DEFAULT '0' COMMENT '固定分钟',
  `FIXED_WEEKDAY` int(11) NOT NULL DEFAULT '0' COMMENT '固定周',
  `FIXED_DAY` int(11) NOT NULL DEFAULT '1' COMMENT '固定日',
  `IS_EXEC` int(2) NOT NULL DEFAULT '0' COMMENT '是否在执行 0-否/1-是',
  `CONDITION` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '检索条件',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='ETL实体映射表';

-- ----------------------------
-- Records of ETL_ENTITY
-- ----------------------------

-- ----------------------------
-- Table structure for `ETL_FIELD`
-- ----------------------------
DROP TABLE IF EXISTS `ETL_FIELD`;
CREATE TABLE `ETL_FIELD` (
  `ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `ENTITY_ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '映射实体id，对应ETL_ENTITY表的id字段',
  `SRC_FIELD_NAME` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '原字段名',
  `DES_FIELD_NAME` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '目标字段名',
  `CREATE_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uk_esd` (`ENTITY_ID`,`SRC_FIELD_NAME`,`DES_FIELD_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='ETL字段映射表';

-- ----------------------------
-- Records of ETL_FIELD
-- ----------------------------

-- ----------------------------
-- Table structure for `ETL_PROJECT`
-- ----------------------------
DROP TABLE IF EXISTS `ETL_PROJECT`;
CREATE TABLE `ETL_PROJECT` (
  `ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `PRJ_NAME` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '项目名称',
  `SRC_DB_ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '源数据id',
  `DES_DB_ID` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '目标数据源id',
  `STATUS` int(1) NOT NULL DEFAULT '0' COMMENT '是否有效 0-是/1-否',
  `CREATE_TIME` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建时间',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='ETL项目表';

-- ----------------------------
-- Records of ETL_PROJECT
-- ----------------------------
INSERT INTO `ETL_PROJECT` VALUES ('0d8c6fff-03e2-42dd-8927-91ebba44ed7d', '本地测试', '858fbbf2-e1b3-47d8-8f25-e2340f5d3406', '94e3af29-fa60-412a-834e-dff5e7a4581a', '0', '1524564291272');

-- ----------------------------
-- Table structure for `PLAT_ENT_FILTER`
-- ----------------------------
DROP TABLE IF EXISTS `PLAT_ENT_FILTER`;
CREATE TABLE `PLAT_ENT_FILTER` (
  `id` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '主键',
  `ep_id` varchar(40) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '实体权限ID',
  `table_name` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '表名',
  `field` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字段',
  `condition` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '条件 =、>=、<=、!=、like',
  `an` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '条件组合  AND/OR',
  `con_value` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '条件值，均用字符串类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='实体下载过滤条件';

-- ----------------------------
-- Records of PLAT_ENT_FILTER
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
  `SYS__DATETIME_STAMP` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT 'yyyyMMddHHmmss',
  `COL__NAME` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '用户名',
  `SYS__CREATE_TIME` bigint(20) DEFAULT '0' COMMENT '系统创建时间',
  `COL__FILE` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '附件',
  PRIMARY KEY (`SYS__ID`),
  UNIQUE KEY `UK_EA4316CB` (`COL__ID`) USING BTREE,
  KEY `IDX_CREATE_TIME` (`SYS__CREATE_TIME`) USING BTREE,
  KEY `IDX_PLATFORM_CODE` (`SYS__PLATFORM_CODE`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of TAB__PERSON
-- ----------------------------
INSERT INTO `TAB__PERSON` VALUES ('11', '1', '1', '40313690', '20180801121212', 'jack', '1533093709000', 'http://localhost:8080/file/download?path=2019/10&attName=1571046660782_93.jar&srcName=rsa.jar');
INSERT INTO `TAB__PERSON` VALUES ('602a4fe6-de9a-478e-903e-2846b8daa97f', '12', '12', '40313690', '20180801113702', 'jack 1111qion', '1533094622532', '');
INSERT INTO `TAB__PERSON` VALUES ('7fa2c021-5e16-4ec7-9b0f-a5c8cf21ce57', '2', '2', '40313690', '20180801113254', 'jack qion', '1533094374153', '');
INSERT INTO `TAB__PERSON` VALUES ('ca2c1ea7-2cfd-449a-9b40-fabc06273da1', '112', '112', '40313690', '20180801113702', '1tomcat', '1533094622531', '');
