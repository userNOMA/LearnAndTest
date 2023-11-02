package com.gvb.glodon;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * SysLearnPrj
 * 14212
 * 2023.10.04 23:01
 *
 * @author zxs
 * description 从excel中读取接口信息，并存储为对象
 */
public class XlsxReader {

    private String no;
    private String apiName;
    private String url;
    private String header;
    private String body;
    private String method;
    private String expect;
    private List<XlsxReader> xlsData;
    private String filePath;

    public void printXlsx(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表

            for (Row row : sheet) {
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING:
                            System.out.print(cell.getStringCellValue() + "\t");
                            break;
                        case NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case BOOLEAN:
                            System.out.print(cell.getBooleanCellValue() + "\t");
                            break;
                        default:
                            System.out.print("\t");
                    }
                }
                System.out.println(); // 换行处理下一行
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public List<XlsxReader> getXlsData() {
        return xlsData;
    }

    public void setXlsData(List<XlsxReader> xlsData) {
        this.xlsData = xlsData;
    }

    public XlsxReader() {
    }

    public XlsxReader(String filePath) {
        this.filePath = filePath;
        this.xlsData = new ArrayList<XlsxReader>();
        try (FileInputStream fis = new FileInputStream(this.filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // 获取第一个工作表

            for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                Row row = sheet.getRow(j);
                XlsxReader xlsxReader = new XlsxReader();
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    Cell cell = row.getCell(i);
                    if (cell == null) {
                        return;
                    }
                    if (i == 0) {
                        xlsxReader.no = readCell(cell);
                    } else if (i == 1) {
                        xlsxReader.apiName = readCell(cell);
                    } else if (i == 2) {
                        xlsxReader.url = readCell(cell);
                    } else if (i == 3) {
                        xlsxReader.header = readCell(cell);
                    } else if (i == 4) {
                        xlsxReader.body = readCell(cell);
                    } else if (i == 5) {
                        xlsxReader.method = readCell(cell);
                    } else if (i == 6) {
                        xlsxReader.expect = readCell(cell);
                    }
                }
                xlsData.add(xlsxReader);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readCell(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
}
