package com.zyj.springboot.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zyj.springboot.demo.core.ResultPage;
import com.zyj.springboot.demo.core.cache.*;
import com.zyj.springboot.demo.dao.StudentInfoDao;
import com.zyj.springboot.demo.entity.StudentInfo;
import com.zyj.springboot.demo.service.StudentInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("studentInfoService")
public class StudentInfoServiceImpl implements StudentInfoService {
    public static final Logger logger = LoggerFactory.getLogger(StudentInfoServiceImpl.class);
    @Resource
    private StudentInfoDao studentInfoDao;

    @Override
    public synchronized StudentInfo insertStudent(StudentInfo student) {
        if (null != student) {
            this.checkStudentInfo(student);
            int res = studentInfoDao.insert(student);
            if (res > 0) {
                int sId = studentInfoDao.getLastId();
                student.setsId(sId);
                return student;
            }
        }
        return null;
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
    //@QueryCache(nameSpace = CacheNameSpace.STUDENT_QUERY)
    //public ResultPage queryStudentList(@QueryCacheKey int pageNum, @QueryCacheKey int pageSize, @QueryCacheKey String keyword) {
    public ResultPage queryStudentList(int pageNum, int pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        List<StudentInfo> list = studentInfoDao.queryForList(keyword);
        ResultPage result = new ResultPage(new PageInfo(list));
        return result;
    }

    @Override
    @QueryCache(nameSpace = CacheNameSpace.STUDENT_QUERY)
    public StudentInfo findStudentById (@QueryCacheKey Integer id){
        StudentInfo info = studentInfoDao.findById(id);
        return info;
    }

    @Override
    public synchronized StudentInfo editStudent(StudentInfo student) {
        if (null != student) {
            this.checkEditStident(student);
            int res = studentInfoDao.update(student);
            if(res > 0) {
                return student;
            }
        }
        return null;
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
            logger.info("参数转换："+sIds.toString());*/
            // 第二种实现：疯转集合
            List<Map> idList = new ArrayList<>();
            Map map = null;
            for (int id: ids) {
                map = new HashMap();
                map.put("id", id);
                idList.add(map);
            }
            //logger.info("集合参数: " + JsonUtils.listToJson(idList));
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
