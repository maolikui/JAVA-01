drop database if exists liquidmall;
drop user if exists 'liquidmall'@'%';
create database liquidmall default character set utf8mb4 collate utf8mb4_unicode_ci;
use liquidmall;
create user 'liquidmall'@'%' identified by '123456';
grant all privileges on liquidmall.* to 'liquidmall'@'%';
flush privileges;