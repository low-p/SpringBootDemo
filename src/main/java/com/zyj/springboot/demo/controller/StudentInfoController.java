package com.zyj.springboot.demo.controller;

import com.zyj.springboot.demo.core.ResultPage;
import com.zyj.springboot.demo.entity.StudentInfo;
import com.zyj.springboot.demo.service.IStudentInfoService;
import com.zyj.springboot.demo.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: StudentInfoController
 * @Description:  学生信息控制层
 * @author: orange
 * @date: 2018/7/30 9:46
 */
@Controller
@RequestMapping(value="/student")
public class StudentInfoController {
    public static final Logger logger = LoggerFactory.getLogger(StudentInfoController.class);
    @Autowired
    private IStudentInfoService studentInfoService;

    /**
     * 跳转学生信息列表页
     * @return  String
     */
    @RequestMapping(value = "/index")
    public String addStudentView(){

        return "student";
    }

    /**
     * 新增学生信息
     * @param student
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addStudent(StudentInfo student){
        String result = "failure";
        try {
            //logger.info("参数>>>>>>>>>>>>>>>>>>>>>>>>"+JsonUtils.objectToJson(student));
            StudentInfo info = this.studentInfoService.insertStudent(student);
            //logger.info("返回值>>>>>>>>>>>>>>>>>>>>>>>>" + res);
            if (null != info && null != info.getsId())
                result = "success";
        } catch (Exception e){
            e.printStackTrace();
            result = "failure";
        }
        Map map = new HashMap();
        map.put("result", result);
        return JsonUtils.objectToJson(map);
    }

    /**
     * 更新学生信息
     * @param student
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editStudent(StudentInfo student){
        String result = "failure";
        try {
            //logger.info("修改参数>>>>>>>>>>>>>>>>>>>>>>>>"+JsonUtils.objectToJson(student));
            StudentInfo info = this.studentInfoService.editStudent(student);
            //logger.info("修改返回值>>>>>>>>>>>>>>>>>>>>>>>>" + res);
            if (null != info)
                result = "success";
        } catch (Exception e){
            e.printStackTrace();
            result = "failure";
        }
        Map map = new HashMap();
        map.put("result", result);
        return JsonUtils.objectToJson(map);
    }

    /**
     * 查询学生信息列表
     * @param pageNum
     * @param pageSize
     * @param keyword
     * @return String
     */
    @ResponseBody
    @RequestMapping(value="/list", method = RequestMethod.GET)
    public String getList(@RequestParam(value="pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value="pageSize", required=false, defaultValue = "10") int pageSize,
            @RequestParam(value = "keyword", required = false) String keyword){
        ResultPage page = this.studentInfoService.queryStudentList(pageNum, pageSize, keyword);
        String json = JsonUtils.objectToJson(page);
        return json;
    }

    /**
     * 批量删除学生信息
     * @param ids
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String bacthDelStudent(@RequestParam(value = "ids") int[] ids){
        String result = "failure";
        try {
            //logger.info("删除参数>>>>>>>>>>>>>>>>>>>>>>>>"+ Arrays.toString(ids));
            int res = this.studentInfoService.batchDelStudent(ids);
            //logger.info("删除返回值>>>>>>>>>>>>>>>>>>>>>>>>" + res);
            if (res > 0)
                result = "success";
        } catch (Exception e){
            e.printStackTrace();
            result = "failure";
        }
        Map map = new HashMap();
        map.put("result", result);
        return JsonUtils.objectToJson(map);
    }

}
