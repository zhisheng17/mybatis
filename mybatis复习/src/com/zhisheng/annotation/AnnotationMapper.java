package com.zhisheng.annotation;

import com.zhisheng.pojo.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by 10412 on 2017/4/12.
 */
public interface AnnotationMapper
{
    @Insert("insert into students(studId,name,email,dob) values(#{studId},#{name},#{email},#{dob})")
    int insertStudent(Student student);

    @Insert("insert into person(id, name, age) values(#{id}, #{name},#{age})")
    //@SelectKey(statement="select my_seq.nextval from dual", keyProperty="id", resultType=int.class, before=true)
    int insertPerson(Person person);

    @Update("update person set name=#{name}, age=#{age} where id=#{id}")
    int updatePerson(Person person);

    @Delete("delete from person where id=#{id}")
    int deletePerson(int id);

    @Select("select studId, name, email, phone from students where studId=#{studId}")
    Student findStudentById(Integer studId);

    //将查询结果通过别名或者是 @Results注解与JavaBean属性映射起来
    @Select("select * from students")
    @Results(
            {
                    @Result(id = true, column = "studId", property = "studId"),
                    @Result(column = "name", property = "name"),
                    @Result(column = "email", property = "email"),
                    @Result(column = "addr_id", property = "address.addrId")
            })
    List<Student> findAllStudents();

//    @One注解来使用嵌套select语句（Nested-Select）加载一对一关联查询数据
/*
    @Select("select addr_id as addrId, street, city, state, zip, country from address where addr_id=#{id}")
    Address findAddressById(int id);

    @Select("select * from students where studId=#{studId} ")
    @Results(
            {
                    @Result(id = true, column = "studId", property = "studId"),
                    @Result(column = "name", property = "name"),
                    @Result(column = "email", property = "email"),
                    @Result(property = "address", column = "addr_id",
                            one = @One(select = "com.zhisheng.annotation.AnnotationMapper.findAddressById"))
            })
    Student selectStudentWithAddress(int studId);
*/

    //MyBatis提供了 @Many注解，用来使用嵌套Select语句加载一对多关联查询。
    @Select("select addr_id as addrId, street, city, state, zip, country from address where addr_id=#{id}")
    Address findAddressById(int id);

    @Select("select * from courses where tutor_id=#{tutorId}")
    @Results(
            {
                    @Result(id = true, column = "course_id", property = "courseId"),
                    @Result(column = "name", property = "name"),
                    @Result(column = "description", property = "description"),
                    @Result(column = "start_date", property = "startDate"),
                    @Result(column = "end_date", property = "endDate")
            })
    List<Course> findCoursesByTutorId(int tutorId);


    @Select("SELECT tutor_id, name as tutor_name, email, adds_id FROM tutors where tutor_id=#{tutorId}")
            @Results(
                    {
                            @Result(id = true, column = "tutor_id", property = "tutorId"),
                            @Result(column = "tutor_name", property = "name"),
                            @Result(column = "email", property = "email"),
                            @Result(property = "address", column = "addr_id",
                                    one = @One(select = "com.zhisheng.annotation.AnnotationMapper.findAddressById")),
                            @Result(property = "courses", column = "tutor_id",
                                    many = @Many(select = "com.zhisheng.annotation.AnnotationMapper.findCoursesByTutorId"))
                    })
    Tutor findTutorById(int tutorId);

}
