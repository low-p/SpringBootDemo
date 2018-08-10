package com.zyj.springboot.demo;

import com.zyj.springboot.demo.core.ExcelStyleType;
import com.zyj.springboot.demo.core.ResultPage;
import com.zyj.springboot.demo.entity.StudentInfo;
import com.zyj.springboot.demo.service.IStudentInfoService;
import com.zyj.springboot.demo.util.ExcelUtils;
import com.zyj.springboot.demo.util.JsonUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExcelPOITest {
    public static final Logger logger = LoggerFactory.getLogger(ExcelPOITest.class);
    @Resource
    private IStudentInfoService studentInfoService;

    @Test
    public void exportStudentInfo() throws IOException {
        ResultPage page = studentInfoService.queryStudentList(1, 50, "");
        List<StudentInfo> list = page.getRows();
        if (list.isEmpty()) throw new RuntimeException("未查询到学生信息");
        //HSSFWorkbook workbook = new HSSFWorkbook();
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("学生信息表");
        Row row = null;
        row = sheet.createRow(0);
        row.setHeight((short) (26.25*20));
        row.createCell(0).setCellValue("学生信息列表");
        //row.getCell(0).setCellStyle(ExcelUtils.getHSSFStyle(workbook, ExcelStyleType.TITLE));
        row.getCell(0).setCellStyle(ExcelUtils.getXSSFStyle(workbook, ExcelStyleType.TITLE));
        for (int i = 1; i < 5; i++) {
            //row.createCell(i).setCellStyle(ExcelUtils.getHSSFStyle(workbook, ExcelStyleType.TITLE));
            row.createCell(i).setCellStyle(ExcelUtils.getXSSFStyle(workbook, ExcelStyleType.TITLE));
        }
        // 合并单元格(列)
        CellRangeAddress rowRegion = new CellRangeAddress(0,0,0,4);
        sheet.addMergedRegion(rowRegion);
        // 合并单元格(行)
        /*CellRangeAddress columnRegion = new CellRangeAddress(1,6,0,0);
        sheet.addMergedRegion(columnRegion);*/

        row = sheet.createRow(1);
        row.setHeight((short) (22.50*20));
        row.createCell(0).setCellValue("学生ID");
        row.createCell(1).setCellValue("学生姓名");
        row.createCell(2).setCellValue("学生班级");
        row.createCell(3).setCellValue("性别");
        row.createCell(4).setCellValue("年龄");
        for (int i = 0; i < 5; i++) {
            //row.getCell(i).setCellStyle(ExcelUtils.getHSSFStyle(workbook, ExcelStyleType.COLUMN));
            row.getCell(i).setCellStyle(ExcelUtils.getXSSFStyle(workbook, ExcelStyleType.COLUMN));
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
                //row.getCell(j).setCellStyle(ExcelUtils.getHSSFStyle(workbook, ExcelStyleType.CONTENT));
                row.getCell(j).setCellStyle(ExcelUtils.getXSSFStyle(workbook, ExcelStyleType.CONTENT));
            }
        }
        // 默认行高
        sheet.setDefaultRowHeight((short) (16.50*20));
        // 列宽自适应
        for (int i = 0; i < 10; i++) {
            sheet.autoSizeColumn(i);
        }
        File studentFile = new File("D:\\ProjectFiles\\download\\student-xlsx.xlsx");
        FileOutputStream fos = new FileOutputStream(studentFile);
        workbook.write(fos);
        fos.close();
    }

    @Test
    public void importStudentExcel() throws IOException {
        String filePath = "D:\\student-xlsx.xlsx";
        File file = new File(filePath);
        String suffix = file.getName().substring(file.getName().lastIndexOf("."));
        Workbook wb;
        InputStream ins = new FileInputStream(file);
        if (".xlsx".equals(suffix)) {
            wb = new XSSFWorkbook(ins);
        } else if (".xls".equals(suffix)) {
            wb = new HSSFWorkbook(ins);
        } else {
            throw new RuntimeException("文件格式不正确");
        }
        int sheetNums = wb.getNumberOfSheets(), rowNums, cellNums;
        logger.info(">>>>>>>>>>>>>>>>>>>>工作表数: " + sheetNums);
        Sheet sheet;
        Row row;
        Cell cell;
        List<StudentInfo> list = new ArrayList<>();
        StudentInfo info;
        // 遍历Sheet
        for (int i = 0; i < sheetNums; i++) {
            sheet = wb.getSheetAt(i);
            rowNums = sheet.getPhysicalNumberOfRows();
            if(i==0) logger.info(">>>>>>>>>>>>>>>>>>>>行数: " + rowNums);
            // 遍历行
            for (int j = 0; j < rowNums; j++) {
                row = sheet.getRow(j);
                cellNums = row.getPhysicalNumberOfCells();
                if(j==0) logger.info(">>>>>>>>>>>>>>>>>>>>列数: " + cellNums);
                if (j == 0 || j == 1) continue;  //标题行和标题栏
                info = new StudentInfo();
                // 遍历列
                for(int k = 0; k < cellNums; k++){
                    cell = row.getCell(k);
                    if(k==0)info.setsName(cell.getStringCellValue());
                    if(k==1)info.setsClass(cell.getStringCellValue());
                    if(k==2)info.setSex(cell.getStringCellValue());
                    if(k==3) {
                        Double age = cell.getNumericCellValue();
                        info.setAge(age.intValue());
                    }
                }
                list.add(info);
            }
        }
        logger.info(">>>>>>>>>>>>>>>>>>>>解析Excel表格数据: " + JsonUtils.listToJson(list));
    }
}
