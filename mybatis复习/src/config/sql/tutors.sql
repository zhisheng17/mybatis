drop table tutors;
create table tutors(
		  tutor_id number primary key,
		  name varchar2(50) not null,
		  email varchar2(50) ,
		  phone varchar2(15) ,
		  adds_id number(11) references address (addr_id)
		);

insert into tutors(tutor_id,name,email,phone,adds_id)
		values(1,'zs','zs@briup.com','123-456-7890',1);
		insert into tutors(tutor_id,name,email,phone,adds_id)
		values(2,'ls','ls@briup.com','111-222-3333',2);

在上述的表数据中，zs 讲师教授两个课程，而 ls 讲师教授一个课程