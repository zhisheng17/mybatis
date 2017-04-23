create table student_course (
		  id number primary key,
		  student_id number references student(id),
		  course_id number references course(id)
		);