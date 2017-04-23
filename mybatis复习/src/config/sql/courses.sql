drop table courses;
create table courses(
		  course_id number primary key,
		  name varchar2(100) not null,
		  description varchar2(512),
		  start_date date ,
		  end_date date ,
		  tutor_id number references tutors (tutor_id)
		);


insert into
		courses(course_id,name,description,start_date,end_date,tutor_id)
		values(1,'JavaSE','JavaSE',to_date('2015-09-10','yyyy-mm-dd'),to_date('2016-02-10','yyyy-mm-dd'),1);

		insert into
		courses(course_id,name,description,start_date,end_date,tutor_id)
		values(2,'JavaEE','JavaEE',to_date('2015-09-10','yyyy-mm-dd'),to_date('2016-03-10','yyyy-mm-dd'),2);

		insert into
		courses(course_id,name,description,start_date,end_date,tutor_id)
		values(3,'MyBatis','MyBatis',to_date('2015-09-10','yyyy-mm-dd'),to_date('2016-02-20','yyyy-mm-dd'),1);