package com.zyj.springboot.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zyj.springboot.demo.dao.StudentInfoDao;
import com.zyj.springboot.demo.entity.StudentInfo;
import com.zyj.springboot.demo.service.StudentInfoService;
import com.zyj.springboot.demo.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("studentInfoService")
public class StudentInfoServiceImpl implements StudentInfoService {

    @Resource
    private StudentInfoDao studentInfoDao;


    @Override
    //@CachePut(value = "student", key = "#root.targetClass + #student.sId", unless = "#student eq null")
    public int insertStudent(StudentInfo student) {
        if (null != student) {
            this.checkStudentInfo(student);
            return studentInfoDao.insert(student);
        }
        return 0;
    }

    /**
     * 验证非空
     */
    private void checkStudentInfo(StudentInfo student) {
        if (StringUtils.isEmpty(student.getsName()))
            throw new RuntimeException("学生姓名为空");
        if (StringUtils.isEmpty(student.getsClass()))
            throw new RuntimeException("学生班级为空");
        if (StringUtils.isEmpty(student.getSex()))
            throw new RuntimeException("学生性别为空");
        if (null == student.getAge())
            throw new RuntimeException("学生年龄为空");
        if (student.getAge() < 1 || student.getAge() > 100)
            throw new RuntimeException("学生年龄非正常范围");
    }

    @Override
    //@Cacheable(value = "studentList", key = "#root.targetClass + #keyword", unless = "#result eq null")
    public PageInfo queryStudentList(int pageNum, int pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        List<StudentInfo> list = studentInfoDao.queryForList(keyword);
        PageInfo result = new PageInfo(list);
        return result;
    }

    @Override
    //@CachePut(value = "student", key = "#root.targetClass + #student.sId", unless = "#student eq null")
    public int editStudent(StudentInfo student) {
        if (null != student) {
            this.checkEditStident(student);
            return studentInfoDao.update(student);
        }
        return 0;
    }

    @Override
    public int batchDelStudent(int[] ids) {
        if (null != ids && ids.length > 0) {
            // 第一种实现：id“,”隔开字符串
            /*StringBuffer sIds = new StringBuffer();
            for (int i = 0; i < ids.length; i++) {
                if (i < (ids.length - 1)) {
                    sIds.append(ids[i]);
                    sIds.append(",");
                } else {
                    sIds.append(ids[i]);
                }
            }
            System.out.println("参数转换："+sIds.toString());*/
            // 第二种实现：疯转集合
            List<Map> idList = new ArrayList<>();
            Map map = null;
            for (int id: ids) {
                map = new HashMap();
                map.put("id", id);
                idList.add(map);
            }
            System.out.println("集合参数：" + JsonUtils.listToJson(idList));
            return this.studentInfoDao.batchDeleteStudent(idList);
            //return this.studentInfoDao.batchDelStudent(sIds.toString());
        }
        return 0;
    }

    /**
     * 修改学生信息
     * @param student
     */
    private void checkEditStident(StudentInfo student) {
        if (null == student.getsId())
            throw new RuntimeException("学生信息不存在");
        if (StringUtils.isEmpty(student.getsName()) && StringUtils.isEmpty(student.getsClass())
                && StringUtils.isEmpty(student.getSex()) && null == student.getAge())
            throw new RuntimeException("修改信息至少一项不为空");
        if (null != student.getAge() && (student.getAge() < 1 || student.getAge() > 100))
            throw new RuntimeException("学生年龄非正常范围");
    }
}
