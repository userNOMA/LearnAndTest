package org.example.poi.test;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhouxs-a
 * @version 1.0
 * @description: 用于识别word内容的工具类
 * @date 2023/11/3 12:43
 */
public class WordUtils {
    private static int basicInfoPointer;
    private static final String BASIC_INFO_TITLE = "清标报告";
    private static int matchCheckPointer;
    private static final String MATCH_CHECK_TITLE = "一、经清标软件检查，符合性检查结果：";
    private static int calcCheckPointer;
    private static final String CALC_CHECK_TITLE = "二、经清标软件检查，计算性检查结果：";
    private static int hardwarePointer;
    private static final String HARDWARE_TITLE = "三、经清标软件检查，软硬件信息检查结果：";
    private static int attachmentPointer;
    private static final String ATTACHMENT_TITLE = "四、附件";
    private static XWPFDocument docx;
    private static List<XWPFParagraph> paragraphs;


    /**
     * @description: 构造方法
     * @return: null
     * @author zhouxs-a
     * @date 2023/11/3 15:45
     */
    public WordUtils() {
        basicInfoPointer = 0;
        matchCheckPointer = 0;
        calcCheckPointer = 0;
        hardwarePointer = 0;
        attachmentPointer = 0;
    }

    /**
     * @description: 按照预期格式读取清标报告
     * @Param docxPath: 清标报告路径
     * @return: java.util.LinkedHashMap<java.lang.String,java.util.LinkedHashMap>
     * @author zhouxs-a
     * @date 2023/11/3 16:13
     */
    public static LinkedHashMap<String, Object> ReadClearReport(String docxPath){
        init(docxPath);
        System.out.println(matchCheckPointer);
        LinkedHashMap<String, Object> clearReport = null;
        clearReport.put("basicInfo", ReadBasicInfo());
//        clearReport.put("matchCheckResult", ReadMatchCheckResult());
//        clearReport.put("calcCheckResult", ReadCalcCheckResult());
//        clearReport.put("hardwareCheckResult", ReadHardwareCheckResult());
//        clearReport.put("attachment", ReadAttachment());
    return clearReport;
    }

    private static LinkedHashMap ReadAttachment() {
        return null;
    }

    private static LinkedHashMap ReadHardwareCheckResult() {
        return null;
    }

    private static LinkedHashMap ReadCalcCheckResult() {
        LinkedHashMap o = null;
        return o;
    }

    private static LinkedHashMap ReadMatchCheckResult() {
        LinkedHashMap o = null;
        LinkedHashMap o1 = o;
        return o1;
    }

    /**
     * @description: 读取基础情况信息
     * @return: java.util.LinkedHashMap<java.lang.String, java.util.LinkedHashMap>
     * @author zhouxs-a
     * @date 2023/11/3 13:01
     */
    private static LinkedHashMap<String, Object> ReadBasicInfo() {
        LinkedHashMap<String, Object> basicInfo = null;
        String regex = "项目名称：(.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(paragraphs.get(basicInfoPointer).getText());
        if (matcher.find()) {
            basicInfo.put("name", matcher.group(1));
        } else {
            basicInfo.put("name", null);
        }
//        basicInfo.put("importTime", paragraphs.get(basicInfoPointer+1));
//        basicInfo.put("exportTime", paragraphs.get(basicInfoPointer+1));
        System.out.println(basicInfo);
        return null;
    }

    /**
     * @description: 初始化段落指针方法
     * @Param docxPath: docx文件的路径
     * @return: void
     * @author zhouxs-a
     * @date 2023/11/3 15:54
     */
    private static void init(String docxPath) {
        try (FileInputStream fis = new FileInputStream(docxPath)) {
            // 打开Word文件
            docx = new XWPFDocument(fis);
            // 打印doc文本内容的同时获取文本的格式
            paragraphs = docx.getParagraphs();
            int i = 0;
            for (XWPFParagraph paragraph : paragraphs) {
                // 打印文本内容，不包含表格中的内容
                switch (paragraph.getText()) {
                    case BASIC_INFO_TITLE:
                        basicInfoPointer = i++;
                    case MATCH_CHECK_TITLE:
                        matchCheckPointer = i++;
                    case CALC_CHECK_TITLE:
                        calcCheckPointer = i++;
                    case HARDWARE_TITLE:
                        hardwarePointer = i++;
                    case ATTACHMENT_TITLE:
                        attachmentPointer = i++;
                    default:
                        System.out.println(paragraph.getText());
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
