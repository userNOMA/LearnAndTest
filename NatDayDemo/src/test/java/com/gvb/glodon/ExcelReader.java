package com.gvb.glodon;

/**
 * SysLearnPrj
 * 14212
 * 2023.10.04 22:35
 *
 * @author zxs
 * description
 */
import java.io.FileInputStream;
import java.io.IOException;

import com.alibaba.fastjson.JSON;
import org.apache.poi.ss.usermodel.*;

public class ExcelReader {

    public static void main(String[] args) {
        String filePath = "NatDayDemo\\src\\test\\resources\\APICases.xlsx";

        XlsxReader xlsxReader = new XlsxReader(filePath);
        XlsxReader xlsxReader0 = xlsxReader.getXlsData().get(0);
        System.out.println(xlsxReader0.getUrl());
        System.out.println(xlsxReader0.getHeader());
        System.out.println(xlsxReader0.getBody());

        BodyInv fileData = JSON.parseObject(xlsxReader0.getBody(), BodyInv.class);
        System.out.println(JSON.parse(xlsxReader0.getHeader()));
        System.out.println(JSON.toJSONString(fileData));

//        try (FileInputStream fis = new FileInputStream(filePath);
//             Workbook workbook = WorkbookFactory.create(fis)) {
//
//            Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表
//
//            for (Row row : sheet) {
//                for (Cell cell : row) {
//                    switch (cell.getCellType()) {
//                        case STRING:
//                            System.out.print(cell.getStringCellValue() + "\t");
//                            break;
//                        case NUMERIC:
//                            System.out.print(cell.getNumericCellValue() + "\t");
//                            break;
//                        case BOOLEAN:
//                            System.out.print(cell.getBooleanCellValue() + "\t");
//                            break;
//                        default:
//                            System.out.print("\t");
//                    }
//                }
//                System.out.println(); // 换行处理下一行
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}