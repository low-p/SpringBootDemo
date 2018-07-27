package com.zyj.springboot.demo.entity;

import java.io.Serializable;

public class StudentInfo implements Serializable {
    private Integer sId;
    private String sName;
    private String sClass;
    private String sex;
    private Integer age;

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsClass() {
        return sClass;
    }

    public void setsClass(String sClass) {
        this.sClass = sClass;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "StudentInfo{sId='"+sId+"', sName='"+sName+"', sClass='"+sClass+"', sex='"+sex+"', age='"+age+"'}";
    }
}
