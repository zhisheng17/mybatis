CREATE TABLE address(
addr_id NUMBER(7) CONSTRAINT address_addr_id_pk PRIMARY KEY ,
street VARCHAR2(10),
city varchar2(10),
state varchar2(10),
zip VARCHAR2(10),
country VARCHAR2(10)
);

create table address(
		  addr_id number primary key,
		  street varchar2(50) not null,
		  city varchar2(50) not null,
		  state varchar2(50) not null,
		  zip varchar2(10),
		  country varchar2(50)
		);

INSERT INTO address VALUES (1, '南京路', '南京', '亚洲', '10000', '中国');
INSERT INTO address VALUES (2, '南昌路', '南昌', '亚洲', '12561', '中国');
INSERT INTO address VALUES (3, '武汉路', '武汉', '亚洲', '10012', '中国');
INSERT INTO address VALUES (4, '北京路', '北京', '亚洲', '12457', '中国');
INSERT INTO address VALUES (5, '上海路', '上海', '亚洲', '55592', '中国');