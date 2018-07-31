package com.zyj.springboot.demo.util;


import com.zyj.springboot.demo.core.ExcelStyleType;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;

public class ExcelUtils {

    /**
     * Excel样式设置(HSSFWorkbook)
     * @param hssfWorkbook
     * @param styleKey
     * @return
     */
    public static HSSFCellStyle getHSSFStyle(HSSFWorkbook hssfWorkbook, ExcelStyleType styleKey){
        HSSFCellStyle style = hssfWorkbook.createCellStyle();
        style.setBorderRight(BorderStyle.THIN);//右边框
        style.setBorderBottom(BorderStyle.THIN);//下边框

        HSSFFont font = hssfWorkbook.createFont();
        font.setFontName("微软雅黑");//设置字体为微软雅黑

        HSSFPalette palette = hssfWorkbook.getCustomPalette();//拿到palette颜色板,可以根据需要设置颜色
        switch (styleKey){
            case TITLE :
                style.setAlignment(HorizontalAlignment.CENTER_SELECTION);//跨列居中
                font.setBold(true);//粗体
                font.setFontHeightInPoints((short) 14);//字体大小
                style.setFont(font);
                palette.setColorAtIndex(HSSFColor.BLUE.index,(byte)184,(byte)204,(byte)228);//替换颜色板中的颜色
                style.setFillForegroundColor(HSSFColor.BLUE.index);
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                break;
            case COLUMN :
                style.setAlignment(HorizontalAlignment.CENTER_SELECTION);
                font.setBold(true);//粗体
                font.setFontHeightInPoints((short) 12);//字体大小
                style.setFont(font);
                break;
            case CONTENT :
                style.setAlignment(HorizontalAlignment.LEFT);
                font.setFontHeightInPoints((short)10);
                style.setFont(font);
                break;
            case COLUMN_TITLE :
                style.setFont(font);
                palette.setColorAtIndex(HSSFColor.GREEN.index,(byte)0,(byte)32,(byte)96);//替换颜色板中的颜色
                style.setFillForegroundColor(HSSFColor.GREEN.index);
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                break;
        }

        return style;
    }

    /**
     * Excel样式设置(XSSFWorkbook)
     * @param xssfWorkbook
     * @param styleKey
     * @return
     */
    public static XSSFCellStyle getXSSFStyle(XSSFWorkbook xssfWorkbook, ExcelStyleType styleKey){
        XSSFCellStyle style = xssfWorkbook.createCellStyle();
        style.setBorderRight(BorderStyle.THIN);//右边框
        style.setBorderBottom(BorderStyle.THIN);//下边框

        XSSFFont font = xssfWorkbook.createFont();
        font.setFontName("微软雅黑");//设置字体为微软雅黑

        switch (styleKey){
            case TITLE :
                style.setAlignment(HorizontalAlignment.CENTER_SELECTION);//跨列居中
                font.setBold(true);//粗体
                font.setFontHeightInPoints((short) 14);//字体大小
                style.setFont(font);
                style.setFillForegroundColor(new XSSFColor(new Color(155, 202, 228))); //设置背景色
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                break;
            case COLUMN :
                style.setAlignment(HorizontalAlignment.CENTER_SELECTION);
                font.setBold(true);//粗体
                font.setFontHeightInPoints((short) 12);//字体大小
                style.setFont(font);
                break;
            case CONTENT :
                style.setAlignment(HorizontalAlignment.LEFT);
                font.setFontHeightInPoints((short)10);
                style.setFont(font);
                break;
            case COLUMN_TITLE :
                style.setFont(font);
                style.setFillForegroundColor(new XSSFColor(new Color(46, 170, 228)));
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                break;
        }
        return style;
    }
}
