package com.zyj.springboot.demo.controller;

import com.github.pagehelper.PageInfo;
import com.zyj.springboot.demo.entity.StudentInfo;
import com.zyj.springboot.demo.service.StudentInfoService;
import com.zyj.springboot.demo.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value="/student")
public class StudentInfoController {

    @Autowired
    private StudentInfoService studentInfoService;

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
        String result = "fail";
        try {
            //System.out.println("参数： "+JsonUtils.objectToJson(student));
            int res = this.studentInfoService.insertStudent(student);
            //System.out.println("返回值： " + res);
            if (res > 0)
                result = "success";
        } catch (Exception e){
            e.printStackTrace();
            result = "fail";
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
        String result = "fail";
        try {
            //System.out.println("修改参数： "+JsonUtils.objectToJson(student));
            int res = this.studentInfoService.editStudent(student);
            //System.out.println("修改返回值： " + res);
            if (res > 0)
                result = "success";
        } catch (Exception e){
            e.printStackTrace();
            result = "fail";
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
        PageInfo page = this.studentInfoService.queryStudentList(pageNum, pageSize, keyword);
        String json = "{\"total\":"+page.getTotal()+",\"rows\":"+JsonUtils.listToJson(page.getList()) +"}";
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
        String result = "fail";
        try {
            //System.out.println("删除参数： "+ Arrays.toString(ids));
            int res = this.studentInfoService.batchDelStudent(ids);
            //System.out.println("删除返回值： " + res);
            if (res > 0)
                result = "success";
        } catch (Exception e){
            e.printStackTrace();
            result = "fail";
        }
        Map map = new HashMap();
        map.put("result", result);
        return JsonUtils.objectToJson(map);
    }

}
