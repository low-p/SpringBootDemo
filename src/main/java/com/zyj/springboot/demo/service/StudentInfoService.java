package com.zyj.springboot.demo.service;

import com.github.pagehelper.PageInfo;
import com.zyj.springboot.demo.entity.StudentInfo;

public interface StudentInfoService {
    /**
     * 新增学生信息
     * @param student
     * @return
     */
    int insertStudent(StudentInfo student);

    /**
     * 分页查询学生信息列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo queryStudentList(int pageNum, int pageSize, String keyword);

    /**
     * 修改学生信息
     * @param student
     * @return
     */
    int editStudent(StudentInfo student);

    /**
     * 批量删除学生信息
     * @param ids
     * @return
     */
    int batchDelStudent(int[] ids);
}
