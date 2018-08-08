package com.zyj.springboot.demo.service;

import com.zyj.springboot.demo.core.ResultPage;
import com.zyj.springboot.demo.entity.StudentInfo;

public interface IStudentInfoService {
    /**
     * 新增学生信息
     * @param student
     * @return
     */
    StudentInfo insertStudent(StudentInfo student);

    /**
     * 分页查询学生信息列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResultPage queryStudentList(int pageNum, int pageSize, String keyword);

    /**
     * ID查询学生信息
     * @param id
     * @return
     */
    StudentInfo findStudentById(Integer id);

    /**
     * 修改学生信息
     * @param student
     * @return
     */
    StudentInfo editStudent(StudentInfo student);

    /**
     * 批量删除学生信息
     * @param ids
     * @return
     */
    int batchDelStudent(int[] ids);
}
