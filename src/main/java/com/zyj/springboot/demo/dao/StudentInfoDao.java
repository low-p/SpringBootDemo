package com.zyj.springboot.demo.dao;

import com.zyj.springboot.demo.entity.StudentInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StudentInfoDao {

    int insert(StudentInfo student);

    List<StudentInfo> queryForList(@Param(value = "keyword") String keyword);

    int update(StudentInfo student);

    int batchDeleteStudent(List<Map> idList);
}
