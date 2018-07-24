# SpringBootDemo  
SpringBoot搭建简单例子----实现学生信息表的CURD  
使用框架：SpringBoot+MyBatis+Thymeleaf+Bootstrap  
数据库：springboottest  
数据表：student_info  
数据库脚本：  
CREATE TABLE `student_info` (  
   `s_id` INT(11) NOT NULL AUTO_INCREMENT,  
   `s_name` VARCHAR(255) NOT NULL,  
   `s_class` VARCHAR(255) NOT NULL,  
   `sex` VARCHAR(255) NOT NULL,  
   `age` INT(2) NOT NULL,  
   PRIMARY KEY (`s_id`)   
 ) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC  
