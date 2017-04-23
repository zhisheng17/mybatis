package com.zhisheng.pojo;

import java.util.List;

/**
 * Created by 10412 on 2017/4/11.
 */
public class Course2
{
    private Integer id;
    private String course_code; // 课程编号
    private String course_name;// 课程名称
    private List<Student2> students;// 选课学生

    public Course2() {
    }

    public Course2(Integer id, String course_code, String course_name, List<Student2> students) {
        this.id = id;
        this.course_code = course_code;
        this.course_name = course_name;
        this.students = students;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public List<Student2> getStudents() {
        return students;
    }

    public void setStudents(List<Student2> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Course2{" +
                "id=" + id +
                ", course_code='" + course_code + '\'' +
                ", course_name='" + course_name + '\'' +
                ", students=" + students +
                '}';
    }
}
