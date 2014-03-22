package com.gxx.record.wedisle;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取吉日Excel工具类
 * User: Gxx
 * Time: 2014-02-12 23:16
 */
public class ReadJiRiExcelUtils
{
    //Excel文件
    public static final String excel = "C:\\Users\\user\\Desktop\\黄道吉日.xls";
    //年份
    public static final String[] years = new String[]{"2014","2015","2016","2017",
            "2018","2019","2020","2021","2022"};
    public static void main(String[] param) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(excel));
        for(String year : years){
            HSSFSheet sheet = workbook.getSheet(year);
            for(int i=1;i<sheet.getLastRowNum();i++)
            {
                //日期	公历	农历	冲	宜	忌	五行	次岁	彭祖百忌
                String date = sheet.getRow(i).getCell(0).getStringCellValue();
                String gongli = sheet.getRow(i).getCell(1).getStringCellValue();
                String nongli = sheet.getRow(i).getCell(2).getStringCellValue();
                String chong = sheet.getRow(i).getCell(3).getStringCellValue();
                String yi = sheet.getRow(i).getCell(4).getStringCellValue();
                String ji = sheet.getRow(i).getCell(5).getStringCellValue();
                String wuxing = sheet.getRow(i).getCell(6).getStringCellValue();
                String cisui = sheet.getRow(i).getCell(7).getStringCellValue();
                String pengzubaiji = sheet.getRow(i).getCell(8).getStringCellValue();
//            System.out.println("日期=[" + date + "]" + ",公历=[" + gongli + "]" + ",农历=[" + nongli + "]"
//                    + ",冲=[" + chong + "]" + ",宜=[" + yi + "]" + ",忌=[" + ji + "]"
//                    + ",五行=[" + wuxing + "]" + ",次岁=[" + cisui + "]" + ",彭祖百忌=[" + pengzubaiji + "]");
                System.out.println("INSERT INTO WEDISLE_GOOD_DAY(DATE, GONGLI, NONGLI, CHONG, YI, JI, WUXING, " +
                        "CISUI, PENGZUBAIJI) VALUES ('" + date + "','" + gongli + "','" + nongli + "','" + chong
                        + "','" + yi + "','" + ji + "','" + wuxing + "','" + cisui + "','" + pengzubaiji+"');");
            }
        }
    }
}
