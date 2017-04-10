create database porn;
create table resources(
  `id` bigint(20) not NULL PRIMARY KEY AUTO_INCREMENT,
  `img_url` varchar(500) ,
  `title` varchar(500),
  `num` int(11),
  `desc` text,
  `img_name` int(11)
)engine=innodb DEFAULT charset=utf8;
alter table `resources` Add column `desc` TEXT  ;
ALTER table `resources` add COLUMN  `img_name` INT;