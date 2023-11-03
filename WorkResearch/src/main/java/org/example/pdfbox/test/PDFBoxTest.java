package org.example.pdfbox.test;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.File;
import java.io.IOException;

/**
 * @author zhouxs-a
 * @version 1.0
 * @description: 测试读取PDF文件
 * @date 2023/11/3 10:04
 */
public class PDFBoxTest {

    public static void main(String[] args) throws IOException {
        String pdfFilePath = "WorkResearch/src/main/resources/清标报告.pdf";

        // Load PDF document
        PDDocument doc = PDDocument.load(new File(pdfFilePath));

        // Create PDF text stripper
        PDFTextStripper stripper = new PDFTextStripper();

        // Get the text content from the PDF document
        String text = stripper.getText(doc);

        // Print the text content to console
        System.out.println(text);

        // Close the PDF document
        doc.close();
    }


}
