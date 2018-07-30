package com.zyj.springboot.demo;

import com.zyj.springboot.demo.core.ExcelStyleType;
import com.zyj.springboot.demo.core.ResultPage;
import com.zyj.springboot.demo.entity.StudentInfo;
import com.zyj.springboot.demo.service.StudentInfoService;
import com.zyj.springboot.demo.util.ExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExcelPOITest {

    @Resource
    private StudentInfoService studentInfoService;

    @Test
    public void exportStudentInfo(){
        ResultPage page = studentInfoService.queryStudentList(1, 50, "");
        List<StudentInfo> list = page.getRows();
        if (list.isEmpty()) throw new RuntimeException("未查询到学生信息");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("学生信息表");
        HSSFRow row = null;
        row = sheet.createRow(0);
        row.setHeight((short) (26.25*20));
        row.createCell(0).setCellValue("学生信息列表");
        row.getCell(0).setCellStyle(ExcelUtils.getStyle(workbook, ExcelStyleType.TITLE));
        for (int i = 1; i < 5; i++) {
            row.createCell(i).setCellStyle(ExcelUtils.getStyle(workbook, ExcelStyleType.TITLE));
        }
        // 合并单元格(列)
        CellRangeAddress rowRegion = new CellRangeAddress(0,0,0,4);
        sheet.addMergedRegion(rowRegion);
        // 合并单元格(行)
        /*CellRangeAddress columnRegion = new CellRangeAddress(1,6,0,0);
        sheet.addMergedRegion(columnRegion);*/

        row = sheet.createRow(1);
        row.setHeight((short) (22.50*20));
        //row.createCell(0).setCellStyle(ExcelUtils.getStyle(workbook, ExcelStyleType.COLUMN_TITLE));
        row.createCell(0).setCellValue("学生ID");
        row.createCell(1).setCellValue("学生姓名");
        row.createCell(2).setCellValue("学生班级");
        row.createCell(3).setCellValue("性别");
        row.createCell(4).setCellValue("年龄");
        for (int i = 0; i < 5; i++) {
            row.getCell(i).setCellStyle(ExcelUtils.getStyle(workbook, ExcelStyleType.COLUMN));
        }

        StudentInfo info = null;
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i+2);
            info = list.get(i);
            row.createCell(0).setCellValue(info.getsId());
            row.createCell(1).setCellValue(info.getsName());
            row.createCell(2).setCellValue(info.getsClass());
            row.createCell(3).setCellValue(info.getSex());
            row.createCell(4).setCellValue(info.getAge());
            for (int j = 0; j < 5; j++) {
                row.getCell(j).setCellStyle(ExcelUtils.getStyle(workbook, ExcelStyleType.CONTENT));
            }
        }
        // 默认行高
        sheet.setDefaultRowHeight((short) (16.50*20));
        // 列宽自适应
        for (int i = 0; i < 10; i++) {
            sheet.autoSizeColumn(i);
        }
        try {
            File studentFile = new File("D:\\ProjectFiles\\download\\student.xls");
            FileOutputStream fos = new FileOutputStream(studentFile);
            workbook.write(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
