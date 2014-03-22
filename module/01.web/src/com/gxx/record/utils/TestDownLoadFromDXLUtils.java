/**
 * File Name:    TestDownLoadFromDXLUtils.java
 *
 * File Desc:    ���Թ�����
 *
 * Product AB:   PAYGATE_1_0_0
 *
 * Product Name: PAYGATE
 *
 * Module Name:  01.core
 *
 * Module AB:    01.core
 *
 * Author:       Gxx
 *
 * History:      2013-09-18 created by Gxx
 */
package com.gxx.record.utils;

//import junit.framework.TestCase;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONTokener;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.util.*;

/**
 * ���Թ�����
 * @author Gxx
 * @version 1.0
 */
public class TestDownLoadFromDXLUtils //extends TestCase
{
    /**
     * �ļ�·��
     */
    public static final String FILE_PATH = "C:\\Users\\user\\Desktop\\1\\�Ƶ�����.xls";

    /**
     * Ҫץȡ���ݵ�����
     */
    public static final String[] YEARS = new String[]{"2011","2012","2013","2014","2015","2016","2017","2018","2019","2020",
            "2021","2022"};

    /**
     * ����excel�ļ�
     * @throws Exception
     */
    public void generate2excel(List<Map<String, String>>[] listsArray) throws Exception {
        System.out.println("==========>���ɿ�ʼSTART~");
        HSSFWorkbook workbook = new HSSFWorkbook();
        for(int i=0;i<listsArray.length;i++)
        {
            List<Map<String, String>> lists = listsArray[i];
            HSSFSheet sheet = workbook.createSheet();
            workbook.setSheetName(i, YEARS[i]);

            CellStyle entityStyle = workbook.createCellStyle();
            entityStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
            entityStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

            HSSFFont font1 = workbook.createFont();
            //font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//�Ӵ�
            font1.setColor(HSSFColor.BLUE.index);//��ɫ
            entityStyle.setFont(font1);

            CellStyle propertyModelStyle = workbook.createCellStyle();
            propertyModelStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
            propertyModelStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

            HSSFFont font2 = workbook.createFont();
            font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//�Ӵ�
            //font2.setColor(HSSFColor.BLUE.index);//��ɫ
            propertyModelStyle.setFont(font2);

            CellStyle propertyStyle = workbook.createCellStyle();
            propertyStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
            propertyStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);


            int count = 0;
            for(Map<String, String> map : lists){
                HSSFRow row = sheet.createRow((short)count++);
                row.setRowStyle(entityStyle);

                int j=0;
                HSSFCell cell = row.createCell(j++);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(map.get("dateStr"));
                cell.setCellStyle(entityStyle);

                HSSFCell cell1 = row.createCell(j++);
                cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell1.setCellValue(map.get("gongLi"));
                cell1.setCellStyle(entityStyle);

                HSSFCell cell2 = row.createCell(j++);
                cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell2.setCellValue(map.get("nongLi"));
                cell2.setCellStyle(entityStyle);

                HSSFCell cell3 = row.createCell(j++);
                cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell3.setCellValue(map.get("chong"));
                cell3.setCellStyle(entityStyle);

                HSSFCell cell4 = row.createCell(j++);
                cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell4.setCellValue(map.get("yi"));
                cell4.setCellStyle(entityStyle);

                HSSFCell cell5 = row.createCell(j++);
                cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell5.setCellValue(map.get("ji"));
                cell5.setCellStyle(entityStyle);

                HSSFCell cell6 = row.createCell(j++);
                cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell6.setCellValue(map.get("wuXing"));
                cell6.setCellStyle(entityStyle);

                HSSFCell cell7 = row.createCell(j++);
                cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell7.setCellValue(map.get("suiCi"));
                cell7.setCellStyle(entityStyle);

                HSSFCell cell8 = row.createCell(j++);
                cell8.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell8.setCellValue(map.get("pengZu"));
                cell8.setCellStyle(entityStyle);
            }
        }
        System.out.println("sheets.size() = " + listsArray.length);
        FileOutputStream fOut = new FileOutputStream(FILE_PATH);
        workbook.write(fOut);

        fOut.flush();
        fOut.close();
        System.out.println("==========>�������END~");
    }

    /**
     * ��������һ���µ�����
     * @param month
     * @return
     */
    public List<Map<String, String>> testDownDaoXiLaMonth(String month)
    {
        System.out.println(month);
        String url = "http://www.daoxila.com/event/jsonp?act=getDateInfo&selectDate="
                + month.substring(0, 4) + "-" + month.substring(4) + "&callback=";
        String data = HttpClientUtils.post(url, "", "UTF-8", "UTF-8");
        String newData = EscapeUtils.unescape(data);//data.replaceAll("\\\\", "%")
        System.out.println(newData = newData.substring(1, newData.length()-1));
        JSONObject object = (JSONObject) new JSONTokener( newData ).nextValue();
        Map ret = new HashMap();
        for ( Iterator iterator = object.entrySet().iterator(); iterator.hasNext(); )
        {
            Map.Entry entry = (Map.Entry) iterator.next();
            ret.put( entry.getKey(), entry.getValue() );
        }
        System.out.println(ret.get("status"));
        System.out.println(ret.get("detail"));
        JSONArray json = JSONArray.fromObject(ret.get("detail"));
        List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
        for(int i=0;i<json.size();i++)
        {
            String dateStr = month + (i+1<10?"0" + (i+1):(i+1));
            JSONObject temp = json.getJSONObject(i);
            String gongLi = (StringUtils.trimToEmpty((String)temp.get("gongli")));
            String nongLi = (StringUtils.trimToEmpty((String)temp.get("nongli")));
            String chong = (StringUtils.trimToEmpty((String)temp.get("chong")));
            String yi = (StringUtils.trimToEmpty((String)temp.get("yi")));
            String ji = (StringUtils.trimToEmpty((String)temp.get("ji")));
            String wuXing = (StringUtils.trimToEmpty((String)temp.get("wuxing")));
            String suiCi = (StringUtils.trimToEmpty((String)temp.get("sueici")));
            String pengZu = (StringUtils.trimToEmpty((String)temp.get("pengzu")));
            System.out.println("dateStr=[" + dateStr + "]," + "gongLi=[" + gongLi + "]," +
                    "nongLi=[" + nongLi + "]," + "chong=[" + chong + "]," +
                    "yi=[" + yi + "]," + "ji=[" + ji + "]," +
                    "wuXing=[" + wuXing + "]," + "suiCi=[" + suiCi + "]" +
                    "pengZu=[" + pengZu + "]");
            Map<String, String> map = new HashMap<String, String>();
            map.put("dateStr", dateStr);
            map.put("gongLi", gongLi);
            map.put("nongLi", nongLi);
            map.put("chong", chong);
            map.put("yi", yi);
            map.put("ji", ji);
            map.put("wuXing", wuXing);
            map.put("suiCi", suiCi);
            map.put("pengZu", pengZu);
            lists.add(map);
        }
        return lists;
    }

    /**
     * ��������һ�������
     * @param year
     */
    public List<Map<String, String>> testDownDaoXiLaYear(String year)
    {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for(int i=1;i<=12;i++)
        {
            String month = year + (i<10?("0" + i):(i));
            list.addAll(testDownDaoXiLaMonth(month));
        }
        return list;
    }

    /**
     * ����ץȡ��������
     */
    public void testYeas()
    {
        List<Map<String, String>>[] listsArray = new List[YEARS.length];
        for(int i=0;i< YEARS.length;i++)
        {
            listsArray[i] = new TestDownLoadFromDXLUtils().testDownDaoXiLaYear(YEARS[i]);
        }

        try {
            new TestDownLoadFromDXLUtils().generate2excel(listsArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
