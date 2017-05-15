SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for conf_project
-- ----------------------------
DROP TABLE IF EXISTS `conf_project`;
CREATE TABLE `conf_project` (
  `ID` int(11) NOT NULL,
  `PROJ_CODE` varchar(32) default NULL COMMENT '项目编码',
  `PROJ_NAME` varchar(32) default NULL COMMENT '项目名称',
  `OWNER_ID` int(11) default NULL COMMENT '项目管理者',
  `DEVELOPMENT_VERSION` int(11) default '0' COMMENT '开发版本',
  `PRODUCTION_VERSION` int(11) default '0' COMMENT '生产版本',
  `PRE_VERSION` int(11) default '0' COMMENT '预发布环境版本',
  `TEST_VERSION` int(11) default '0' COMMENT '测试版本',
  `DELETE_FLAG` int(1) default '0' COMMENT '删除标识',
  `CREATE_TIME` datetime default NULL,
  `UPDATE_TIME` datetime default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for conf_project_config
-- ----------------------------
DROP TABLE IF EXISTS `conf_project_config`;
CREATE TABLE `conf_project_config` (
  `CONFIG_ID` int(11) NOT NULL,
  `CONFIG_KEY` varchar(64) NOT NULL,
  `CONFIG_VALUE` varchar(256) NOT NULL,
  `CONFIG_DESC` varchar(256) default NULL,
  `PROJECT_ID` int(11) NOT NULL,
  `MODULE_ID` int(11) NOT NULL,
  `DELETE_FLAG` int(1) default '0',
  `OPT_USER` varchar(32) default NULL,
  `OPT_TIME` datetime default NULL,
  `PRODUCTION_VALUE` varchar(256) NOT NULL,
  `PRODUCTION_USER` varchar(32) default NULL,
  `PRODUCTION_TIME` datetime default NULL,
  `TEST_VALUE` varchar(256) NOT NULL,
  `TEST_USER` varchar(32) default NULL,
  `TEST_TIME` datetime default NULL,
  `PRE_VALUE` varchar(256) default NULL,
  `PRE_USER` varchar(32) default NULL,
  `PRE_TIME` datetime default NULL,
  PRIMARY KEY  (`CONFIG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for conf_project_module
-- ----------------------------
DROP TABLE IF EXISTS `conf_project_module`;
CREATE TABLE `conf_project_module` (
  `MODULE_ID` int(11) NOT NULL,
  `PROJ_ID` int(11) NOT NULL,
  `MODULE_NAME` varchar(32) default NULL,
  PRIMARY KEY  (`MODULE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for conf_project_user
-- ----------------------------
DROP TABLE IF EXISTS `conf_project_user`;
CREATE TABLE `conf_project_user` (
  `PROJ_ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL default '0',
  PRIMARY KEY  (`PROJ_ID`,`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for conf_project_user_role
-- ----------------------------
DROP TABLE IF EXISTS `conf_project_user_role`;
CREATE TABLE `conf_project_user_role` (
  `PROJ_ID` int(11) NOT NULL,
  `USER_ID` int(11) NOT NULL,
  `ROLE_CODE` varchar(32) NOT NULL,
  PRIMARY KEY  (`PROJ_ID`,`USER_ID`,`ROLE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for conf_user
-- ----------------------------
DROP TABLE IF EXISTS `conf_user`;
CREATE TABLE `conf_user` (
  `ID` int(11) NOT NULL,
  `USER_CODE` varchar(32) default NULL COMMENT '用户代码',
  `USER_NAME` varchar(32) NOT NULL COMMENT '用户姓名',
  `PASSWORD` varchar(32) NOT NULL COMMENT '用户密码',
  `DELETE_FLAG` int(1) default '0' COMMENT '删除标识',
  `CREATE_TIME` datetime default NULL,
  `UPDATE_TIME` datetime default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




  <resultMap id="BaseResultMap" type="com.github.diamond.web.model.ConfHistory" >
    <id column="HIS_ID" property="hisId" jdbcType="INTEGER" />
    <result column="CONFIG_ID" property="configId" jdbcType="INTEGER" />
    <result column="CONFIG_KEY" property="configKey" jdbcType="VARCHAR" />
    <result column="CONFIG_VALUE" property="configValue" jdbcType="LONGVARCHAR" />
    <result column="OPT_USER" property="optUser" jdbcType="VARCHAR" />
    <result column="OPT_TIME" property="optTime" jdbcType="TIMESTAMP" />
    <result column="TYPE" property="type" jdbcType="VARCHAR" />
  </resultMap>
  
-- ----------------------------
-- Table structure for conf_history
-- ----------------------------
DROP TABLE IF EXISTS `conf_history`;
CREATE TABLE `conf_history` (
  `HIS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CONFIG_ID` int(11) NOT NULL,
  `CONFIG_KEY` varchar(64) NOT NULL,
  `CONFIG_VALUE` varchar(256) NOT NULL,
  `OPT_USER` varchar(64) NOT NULL,
  `OPT_TIME` datetime DEFAULT NULL,
  `TYPE` varchar(64) NOT NULL,
  PRIMARY KEY (`HIS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

