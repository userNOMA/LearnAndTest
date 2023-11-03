package org.example.pdfbox.test;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

/**
 * @author zhouxs-a
 * @version 1.0
 * @description: 读取pdf文字属性
 * @date 2023/11/3 10:19
 */
public class TextAttributesExtractor extends PDFTextStripper {

    public TextAttributesExtractor() throws IOException {
        super();
    }

    public static void main(String[] args) {
        String pdfFilePath = "WorkResearch/src/main/resources/清标报告.pdf";

        try {
            // Load the PDF document
            PDDocument document = PDDocument.load(new File(pdfFilePath));

            // Create an instance of the TextAttributesExtractor
            TextAttributesExtractor textExtractor = new TextAttributesExtractor();

            // Extract text with attributes
            textExtractor.setSortByPosition(true);
            textExtractor.setAddMoreFormatting(true);
            textExtractor.setSuppressDuplicateOverlappingText(false);
            textExtractor.setStartPage(0);
            textExtractor.setEndPage(document.getNumberOfPages());
            textExtractor.getText(document);

            // Close the document
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void processTextPosition(TextPosition text) {
        // Get text attributes
        float fontSize = text.getFontSize();
        float x = text.getX();
        float y = text.getY();
        String fontName = text.getFont().getName();
        int fontWeight = (int) text.getFont().getFontDescriptor().getFontWeight();

        // Do something with the text attributes
        System.out.println("Text: " + text.getUnicode());
        System.out.println("Font size: " + fontSize);
        System.out.println("Position: (" + x + ", " + y + ")");
        System.out.println("Font name: " + fontName);
        System.out.println("Font weight: " + fontWeight);
        System.out.println();
    }
}
