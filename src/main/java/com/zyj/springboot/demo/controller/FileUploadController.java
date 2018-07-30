package com.zyj.springboot.demo.controller;

import com.zyj.springboot.demo.util.JsonUtils;
import com.zyj.springboot.demo.util.UUIDUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: FileUploadController
 * @Description:  文件上传控制层
 * @author: orange
 * @date: 2018/7/30 10:39
 */
@Controller
@RequestMapping(value = "/upload")
public class FileUploadController {

    /**
     * 跳转上传页面
     * @return
     */
    @RequestMapping(value = "/home")
    public String uploadIndex() {

        return "upload";
    }

    /**
     * 上传文件
     * @param uploadFile
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public String uploadFile(@RequestParam(value = "upload") MultipartFile uploadFile){
        String result = "failure";
        try {
            if (uploadFile.isEmpty()) return "文件不存在";
            String originName = uploadFile.getOriginalFilename();
            System.out.println("文件名>>>>>>" + originName);
            String suffix = originName.substring(originName.lastIndexOf("."));
            System.out.println("后缀名>>>>>>" + suffix);
            String fileName = UUIDUtils.UUIDStr() + suffix;
            System.out.println("生成新文件名>>>>>>" + fileName);
            String filePath = "D:\\ProjectFiles\\upload\\";
            String path = filePath + fileName;
            File file = new File(path);
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            uploadFile.transferTo(file);
            result = "success";
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map resMap = new HashMap<>();
        resMap.put("result", result);
        return JsonUtils.objectToJson(resMap);
    }

}
