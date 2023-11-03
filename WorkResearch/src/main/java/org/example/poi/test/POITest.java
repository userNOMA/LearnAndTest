package org.example.poi.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ooxml.POIXMLProperties;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;

/**
 * @author zhouxs-a
 * @version 1.0
 * @description: 测试POI读取word
 * @date 2023/11/3 1:42
 */
public class POITest {
    public static void main(String[] args) throws IOException {
        String docFilePath = "WorkResearch/src/main/resources/清标报告.docx";
//        PrintDocxAllText(docFilePath);
//        PrintRunFormat(docFilePath);
//        CompareParagraphAndRun(docFilePath);
//        PrintTables(docFilePath);
//        PrintRunPictureData(docFilePath);
    }

    /**
     * @description: 打印每个run当中的图片信息
     * @Param filePath: docx文件路径
     * @return: void
     * @author zhouxs-a
     * @date 2023/11/3 9:47
     */
    private static void PrintRunPictureData(String filePath) {

        try (FileInputStream fis = new FileInputStream(filePath)) {
            // 打开Word文件
            XWPFDocument doc = new XWPFDocument(fis);
            // 打印doc文本内容的同时获取文本的格式
            List<XWPFParagraph> paragraphs = doc.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                // 打印文本内容，不包含表格中的内容
                System.out.println(paragraph.getText());
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs ) {
                    for (XWPFPicture picture : run.getEmbeddedPictures()) {
                        System.out.println(picture.getWidth());
                    }
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @description: 打印docx中所有的表
     * @Param filePath: docx文件路径
     * @return: void
     * @author zhouxs-a
     * @date 2023/11/3 2:07
     */
    private static void PrintTables(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            // 打开Word文件
            XWPFDocument doc = new XWPFDocument(fis);
            // 打印doc文本内容的同时获取文本的格式
            List<XWPFTable> tables = doc.getTables();
            for (XWPFTable table : tables) {
                // 打印文本内容，不包含表格中的内容
                System.out.println(table.getText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 对比段落和run之间的关系
     * @Param filePath: word文件路径
     * @return: void
     * @author zhouxs-a
     * @date 2023/11/3 2:04
     */
    private static void CompareParagraphAndRun(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            // 打开Word文件
            XWPFDocument doc = new XWPFDocument(fis);
            // 打印doc文本内容的同时获取文本的格式
            List<XWPFParagraph> paragraphs = doc.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                // 打印文本内容，不包含表格中的内容
                System.out.println(paragraph.getText());
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs ) {
                    System.out.println(run.toString());
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 打印word中的全部文字
     * @Param filePath: word文件的相对路径
     * @author zhouxs-a
     * @date 2023/11/3 1:44
     */
    private static void PrintDocxAllText(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            // 打开Word文件
            XWPFDocument doc = new XWPFDocument(fis);
            XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
            // 打印doc文本内容
            String text = extractor.getText();
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description: 打印每个run的格式
     * @Param filePath: word文件的相对路径
     * @return: void
     * @author zhouxs-a
     * @date 2023/11/3 1:46
     */
    public static void PrintRunFormat(String filePath) throws IOException {
        FileInputStream fis = null;
        try {
            // 打开Word文件
            fis = new FileInputStream(filePath);
            XWPFDocument doc = new XWPFDocument(fis);
            // 打印doc文本内容的同时获取文本的格式
            List<XWPFParagraph> paragraphs = doc.getParagraphs();
            for (XWPFParagraph paragraph : paragraphs) {
                // 打印文本内容，不包含表格中的内容
                System.out.println(paragraph.getText());
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    // 打印对应文字内容
                    System.out.println(run.getText(0));
                    // 打印对应字体
                    System.out.println(run.getFontName());
                    // 打印对应字号
                    System.out.println(run.getFontSize());
                    // 打印是否加粗
                    System.out.println(run.isBold());
                    // 打印对齐情况
                    System.out.println(run.getTextPosition());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fis.close();
        }
    }


}