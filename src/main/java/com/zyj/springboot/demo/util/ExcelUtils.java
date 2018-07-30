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

public class ExcelUtils {

    /**
     * Excel样式设置
     * @param hssfWorkbook
     * @param styleKey
     * @return
     */
    public static HSSFCellStyle getStyle(HSSFWorkbook hssfWorkbook, ExcelStyleType styleKey){
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
            case COLUMN :{
                font.setBold(true);//粗体
                font.setFontHeightInPoints((short) 12);//字体大小
                style.setFont(font);
            }
            break;
            case CONTENT :{
                font.setFontHeightInPoints((short)10);
                style.setFont(font);
            }
            break;
            case COLUMN_TITLE :{
                style.setFont(font);

                palette.setColorAtIndex(HSSFColor.GREEN.index,(byte)0,(byte)32,(byte)96);//替换颜色板中的颜色
                style.setFillForegroundColor(HSSFColor.GREEN.index);
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            }
            break;
        }

        return style;
    }
}
