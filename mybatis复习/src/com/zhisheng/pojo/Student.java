package com.zhisheng.pojo;

import java.util.Date;

/**
 * Created by 10412 on 2017/4/6.
 */
public class Student
{
    private Integer studId;
    private String name;
    private String email;
    private Date dob;
    private PhoneNumber phone;
    private Address address;

    public PhoneNumber getPhone() {
        return phone;
    }

    public void setPhone(PhoneNumber phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studId=" + studId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                ", phone=" + phone +
                ", address=" + address +
                '}';
    }

    public Student(Integer studId, String name, String email, Date dob, PhoneNumber phone) {
        this.studId = studId;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.phone = phone;
    }

    public Student(Integer studId, String name, String email, Date dob, PhoneNumber phone, Address address) {
        this.studId = studId;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.phone = phone;
        this.address = address;
    }

    public PhoneNumber getPn() {
        return phone;
    }

    public void setPn(PhoneNumber phone) {
        this.phone = phone;
    }

    public Student() {
    }

    public Student(Integer studId, String name, String email, Date dob) {
        this.studId = studId;
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    public Integer getStudId() {
        return studId;
    }

    public void setStudId(Integer studId) {
        this.studId = studId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

}
