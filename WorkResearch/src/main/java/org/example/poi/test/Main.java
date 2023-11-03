package org.example.poi.test;

/**
 * @author zhouxs-a
 * @version 1.0
 * @description: TODO
 * @date 2023/11/3 16:30
 */
public class Main {
    public static void main(String[] args) {
        String docxPath = "WorkResearch/src/main/resources/清标报告.docx";
        WordUtils.ReadClearReport(docxPath);
    }
}
