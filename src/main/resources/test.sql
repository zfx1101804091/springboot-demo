/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-08-06 00:00:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `password` varchar(40) NOT NULL,
  `phone` varchar(60) DEFAULT NULL,
  `Email_a` varchar(255) DEFAULT NULL,
  `k_time` datetime DEFAULT NULL,
  `Administrator_a` varchar(40) DEFAULT NULL,
  `k_state` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'x2ingxing', '222333', '2222', '3333333', '2019-07-11 19:38:22', '董事长', '2');
INSERT INTO `user` VALUES ('4', 'root1', '222333', '15235068202', '3333333', '2019-07-11 19:38:22', '董事长', '2');
INSERT INTO `user` VALUES ('16', '刘俊t', '222333', '15235068204', '3333333', '2019-07-11 19:38:28', '小喽喽', '2');
INSERT INTO `user` VALUES ('107', 'root5', '222333', '1523506820', '3333333', '2019-07-11 19:38:22', '董事长', '2');
INSERT INTO `user` VALUES ('108', 'zhangsan111', '222333', '11111111111', '3333333', '2019-08-02 07:05:18', '222', '2');
INSERT INTO `user` VALUES ('111', 'zhangsa88', '222333', '1113333', '3333333', '2019-08-02 07:06:59', '222', '2');
INSERT INTO `user` VALUES ('112', 'xiaomixiao', '222333', '111111123', '3333333', '2019-08-03 15:38:03', '66', '2');
INSERT INTO `user` VALUES ('113', 'xiaomix2iao', '222333', '1111121123', '3333333', '2019-08-03 15:39:19', '66', '2');
INSERT INTO `user` VALUES ('114', 'xiaomixiaott', '222333', '1111112123', '3333333', '2019-08-03 18:16:52', '66', '2');
INSERT INTO `user` VALUES ('115', 'zhangsan11121q', '1', '11112231111', '1', '2019-08-04 12:31:54', '1', '2');
INSERT INTO `user` VALUES ('116', 'zhangsan141', '1', '11111114111', '1', '2019-08-04 12:44:58', '222', '2');
INSERT INTO `user` VALUES ('117', 'zhengfengxiang', '22222', '13245678763', '1875586562@qq.com', '2019-08-04 20:47:43', '老师', '2');

-- ----------------------------
-- Table structure for user1
-- ----------------------------
DROP TABLE IF EXISTS `user1`;
CREATE TABLE `user1` (
  `id` int(50) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `perms` varchar(50) DEFAULT NULL,
  `edit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user1
-- ----------------------------
INSERT INTO `user1` VALUES ('1', 'jack', '123', 'user:add', '2019-07-31 07:20:02');
INSERT INTO `user1` VALUES ('2', 'rose', '234', 'user:update', '2019-08-05 22:38:43');

-- ----------------------------
-- Procedure structure for insert_user
-- ----------------------------
DROP PROCEDURE IF EXISTS `insert_user`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_user`(IN `p_json` varchar(1000))
BEGIN
	#Routine body goes here...
	DECLARE $num int;
  DECLARE $n_name,$n_phone int;
	declare $result varchar(255);
 
	SET $num=0;set $result='';

#这块是解析服务端传过来的接送json
SELECT REPLACE(JSON_EXTRACT(p_json,"$.name"),'"','')
  ,REPLACE(JSON_EXTRACT(p_json,"$.password"),'"','')
  ,REPLACE(JSON_EXTRACT(p_json,"$.phone"),'"','')
  ,REPLACE(JSON_EXTRACT(p_json,"$.Email_a"),'"','')
  ,REPLACE(JSON_EXTRACT(p_json,"$.Administrator_a"),'"','')
  into @q_name,@q_password,@q_phone,@q_Email_a,@q_Administrator_a; 


	SELECT COUNT(*) into $num from user where name = @q_name or phone = @q_phone;

	IF $num=0 then 
		INSERT into user VALUES(null,@q_name,@q_password,@q_phone,@q_Email_a,CURRENT_TIMESTAMP,@q_Administrator_a,2);
		set $result=JSON_OBJECT('code', 'success','message','新增成功','timestate',unix_timestamp());end if;
	IF $num=2 then set $result=JSON_OBJECT('code', 'fail','message','新增失败,用户名和手机号都已存在','timestate',unix_timestamp());end if;
	IF $num=1 then
			SELECT COUNT(*) INTO $n_name from user where name = @q_name;
			SELECT COUNT(*) INTO $n_phone from user where name = @q_phone;
			if $n_name=1 THEN set $result=JSON_OBJECT('code', 'fail_n','message','新增失败,用户名已存在','timestate',unix_timestamp());
			ELSE
			set $result=JSON_OBJECT('code', 'fail_p','message','新增失败,手机号已存在','timestate',unix_timestamp());
			END IF;
	end if;

	SELECT $result;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for query_user
-- ----------------------------
DROP PROCEDURE IF EXISTS `query_user`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `query_user`(IN `t_username` varchar(255),IN `t_password` varchar(255))
BEGIN
	#Routine body goes here...

	#查询用户是否存在

	#SELECT COUNT(1) into @num from `user` where `name`=t_username and `password` = t_password;
	SELECT * from `user` where `name`=t_username and `password` = t_password ;
	#JSON_OBJECT(@json);
	#SELECT @json;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for test
-- ----------------------------
DROP PROCEDURE IF EXISTS `test`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `test`(IN `name` varchar(255))
BEGIN
	#Routine body goes here...

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for update_user
-- ----------------------------
DROP PROCEDURE IF EXISTS `update_user`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_user`(IN `p_json` varchar(1000))
BEGIN
	
	SELECT REPLACE(JSON_EXTRACT(p_json,"$.name"),'"','')
  ,REPLACE(JSON_EXTRACT(p_json,"$.password"),'"','')
  ,REPLACE(JSON_EXTRACT(p_json,"$.phone"),'"','')
  ,REPLACE(JSON_EXTRACT(p_json,"$.Email_a"),'"','')
  ,REPLACE(JSON_EXTRACT(p_json,"$.Administrator_a"),'"','')
  into @q_name,@q_password,@q_phone,@q_Email_a,@q_Administrator_a; 

	
	UPDATE `user` set k_state = 2 where k_state=p_name;

END
;;
DELIMITER ;
