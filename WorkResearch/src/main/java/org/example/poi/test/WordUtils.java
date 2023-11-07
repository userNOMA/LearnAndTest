package org.example.poi.test;

import org.apache.poi.wp.usermodel.Paragraph;
import org.apache.poi.xwpf.usermodel.*;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
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
    private static final String ATTACHMENT_TITLE_1 = "三、附件";
    private static final String SIGNATURE_TITLE = "五、签字栏：";
    private static final String SIGNATURE_TITLE_1 = "四、签字栏：";
    private static boolean hasHardware = true;
    private static int signaturePointer;
    private static List<XWPFParagraph> paragraphs;
    private static List<XWPFTable> tables;
    private static String docxName;

    /**
     * @description: 不带参数的构造方法，采用默认的初始化报告大纲
     * @return: null
     * @author zhouxs-a
     * @date 2023/11/3 15:45
     */
    public WordUtils() {}

    /**
     * @description: 按照预期格式读取清标报告
     * @Param docxPath: 清标报告路径
     * @return: java.util.LinkedHashMap<java.lang.String, java.util.LinkedHashMap>
     * @author zhouxs-a
     * @date 2023/11/3 16:13
     */
    public static LinkedHashMap<String, Object> ReadClearReport(String docxPath) {
        init(docxPath);
        LinkedHashMap<String, Object> clearReport = new LinkedHashMap<>();
        clearReport.put("basicInfo", ReadBasicInfo());
        clearReport.put("matchCheckResult", ReadMatchCheckResult());
        clearReport.put("calcCheckResult", ReadCalcCheckResult());
        clearReport.put("hardwareCheckResult", ReadHardwareCheckResult());
        clearReport.put("attachment", ReadAttachment());
        return clearReport;
    }

    /**
     * @description: 解析附件部分的内容
     * @return: java.util.LinkedHashMap
     * @author zhouxs-a
     * @date 2023/11/5 13:23
     */
    private static LinkedHashMap ReadAttachment() {
        LinkedHashMap<String, Object> attachmentCheck = new LinkedHashMap<>();
        if (attachmentPointer == 0) {
            attachmentCheck.put("attachment", null);
        }
        int paragraphCount = signaturePointer - attachmentPointer;
        String regex = "(.*?)、《(.*)》";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        ArrayList <LinkedHashMap> attachmentList = new ArrayList<>();
        for (int i = 0; i < paragraphCount - 2; i++) {
            LinkedHashMap <String, String> attachmentRow = new LinkedHashMap<>();
            matcher = pattern.matcher(paragraphs.get(attachmentPointer + i).getText());
            if (matcher.find()) {
                attachmentRow.put("attachmentNo", matcher.group(1));
                attachmentRow.put("attachmentName", matcher.group(2));
            }
            attachmentList.add(attachmentRow);
        }
        attachmentCheck.put("attachment", attachmentList);
        return attachmentCheck;
    }

    /**
     * @description: 解析软硬件检查部分的内容
     * @return: java.util.LinkedHashMap
     * @author zhouxs-a
     * @date 2023/11/5 13:06
     */
    private static LinkedHashMap ReadHardwareCheckResult() {
        LinkedHashMap<String, Object> hardwareCheck = new LinkedHashMap<>();
        if (hardwarePointer == 0) {
            return hardwareCheck;
        }

        int paragraphCount = attachmentPointer - hardwarePointer;

        String regex = "其中投标单位（(.*?)）电子版投标文件出现相同加密锁的信息（(.*?)）。";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        LinkedHashMap<String, List> hardwareCheckList = new LinkedHashMap<>();
        for (int i = 0; i < paragraphCount - 4; i++) {
            if ("".equals(paragraphs.get(hardwarePointer + i).getText())) {
                continue;
            }
            matcher = pattern.matcher(paragraphs.get(hardwarePointer + i).getText());
            if (matcher.find()) {
                hardwareCheckList.put(matcher.group(2), Arrays.asList(matcher.group(1).split("；")));
            }
        }
        hardwareCheck.put("hardwareCheckList", hardwareCheckList);

        XWPFTable table = tables.get(3);
        List<LinkedHashMap<String, Object>> hardwareCheckTable = new ArrayList<>();
        for (int i = 1; i < table.getNumberOfRows(); i++) {
            LinkedHashMap<String, Object> tableRow = new LinkedHashMap<>();
            String number = table.getRow(i).getCell(0).getParagraphs().get(0).getNumLevelText();
            if ("%1".equals(number)) {
                tableRow.put("tendererNo", String.valueOf(i));
            } else {
                Assert.fail(String.format("文件：%s，硬件信息检查表，序号有误！", docxName));
            }
            tableRow.put("tendererName", table.getRow(i).getCell(1).getText());
            ArrayList dogInfoVOSList = new ArrayList();
            for (XWPFParagraph par : table.getRow(i).getCell(2).getParagraphs()) {
                dogInfoVOSList.add(par.getText());
            }
            tableRow.put("dogInfoVOS", dogInfoVOSList);
            ArrayList macAddrVOSList = new ArrayList();
            for (XWPFParagraph par : table.getRow(i).getCell(3).getParagraphs()) {
                macAddrVOSList.add(par.getText());
            }
            tableRow.put("macAddrVOS", macAddrVOSList);
            hardwareCheckTable.add(tableRow);
        }
        hardwareCheck.put("hardwareCheckTable", hardwareCheckTable);

        if (paragraphs.get(hardwarePointer + paragraphCount - 3).getText().contains("不参与")) {
            hardwareCheck.put("tendereeInclude", false);
        }
        else {
            hardwareCheck.put("tendereeInclude", true);
        }

        regex = "详见附表(.*)。";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(paragraphs.get(hardwarePointer + paragraphCount - 2).getText());
        List<LinkedHashMap<String, String>> attachmentList = new ArrayList<>();
        if (matcher.find()) {
            String[] tableName = matcher.group(1).split("、");
            for (int i = 0; i < tableName.length; i++) {
                LinkedHashMap<String, String> tab = new LinkedHashMap<>();
                String reg = "(.*)《(.*)》";
                Pattern pat = Pattern.compile(reg);
                Matcher mat = pat.matcher(tableName[i]);
                if (mat.find()) {
                    tab.put(mat.group(1), mat.group(2));
                }
                attachmentList.add(tab);
            }
            hardwareCheck.put("attachmentList", attachmentList);
        } else {
            hardwareCheck.put("attachmentList", null);
        }
        return hardwareCheck;
    }

    /**
     * @description: 解析清标报告的计算性结果部分
     * @return: java.util.LinkedHashMap
     * @author zhouxs-a
     * @date 2023/11/4 23:33
     */
    private static LinkedHashMap ReadCalcCheckResult() {
        LinkedHashMap<String, Object> calcCheck = new LinkedHashMap<>();
        if (calcCheckPointer == 0) {
            return calcCheck;
        }
        String regex = "计算性检查中(.*?)家单位无计算逻辑错误，(.*?)家有错误";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(paragraphs.get(calcCheckPointer).getText());
        if (matcher.find()) {
            calcCheck.put("errorTendererCount", matcher.group(1));
            calcCheck.put("correctTendererCount", matcher.group(2));
        } else {
            calcCheck.put("errorTendererCount", null);
            calcCheck.put("correctTendererCount", null);
        }

        XWPFTable table = tables.get(2);
        List<LinkedHashMap<String, String>> calcCheckTable = new ArrayList<>();
        for (int i = 1; i < table.getNumberOfRows(); i++) {
            LinkedHashMap<String, String> tableRow = new LinkedHashMap<>();
            String number = table.getRow(i).getCell(0).getParagraphs().get(0).getNumLevelText();
            if ("%1".equals(number)) {
                tableRow.put("tendererNo", String.valueOf(i));
            } else {
                Assert.fail(String.format("文件：%s，计算性检查表，序号有误！", docxName));
            }
            tableRow.put("tendererName", table.getRow(i).getCell(1).getText());
            tableRow.put("errorItemCount", table.getRow(i).getCell(2).getText());
            calcCheckTable.add(tableRow);
        }
        calcCheck.put("machCheckTable", calcCheckTable);

        regex = "详见附表(.*)。";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(paragraphs.get(calcCheckPointer + 1).getText());
        List<LinkedHashMap<String, String>> attachmentList = new ArrayList<>();
        if (matcher.find()) {
            String[] tableName = matcher.group(1).split("、");
            for (int i = 0; i < tableName.length; i++) {
                LinkedHashMap<String, String> tab = new LinkedHashMap<>();
                String reg = "(.*)《(.*)》";
                Pattern pat = Pattern.compile(reg);
                Matcher mat = pat.matcher(tableName[i]);
                if (mat.find()) {
                    tab.put(mat.group(1), mat.group(2));
                }
                attachmentList.add(tab);
            }
            calcCheck.put("attachmentList", attachmentList);
        } else {
            calcCheck.put("attachmentList", null);
        }

        return calcCheck;
    }

    /**
     * @description: 读取符合性检查结果
     * @return: java.util.LinkedHashMap<java.lang.String, java.lang.Object>
     * @author zhouxs-a
     * @date 2023/11/4 0:28
     */
    private static LinkedHashMap<String, Object> ReadMatchCheckResult() {
        LinkedHashMap<String, Object> matchCheck = new LinkedHashMap<>();
        if (matchCheckPointer == 0){
            return matchCheck;
        }

        String regex = "符合性检查中(.*?)家单位.*均一致，(.*?)家不一致";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(paragraphs.get(matchCheckPointer).getText());
        if (matcher.find()) {
            matchCheck.put("differenceTendererCount", matcher.group(1));
            matchCheck.put("sameTendererCount", matcher.group(2));
        } else {
            matchCheck.put("differenceTendererCount", null);
            matchCheck.put("sameTendererCount", null);
        }

        XWPFTable table = tables.get(1);
        List<LinkedHashMap<String, String>> machCheckTable = new ArrayList<>();
        for (int i = 1; i < table.getNumberOfRows(); i++) {
            LinkedHashMap<String, String> tableRow = new LinkedHashMap<>();
            String number = table.getRow(i).getCell(0).getParagraphs().get(0).getNumLevelText();
            if ("%1".equals(number)) {
                tableRow.put("tendererNo", String.valueOf(i));
            } else {
                Assert.fail(String.format("文件：%s，符合性检查表，序号有误！", docxName));
            }
            tableRow.put("tendererName", table.getRow(i).getCell(1).getText());
            tableRow.put("addItemCount", table.getRow(i).getCell(2).getText());
            tableRow.put("deleteItemCount", table.getRow(i).getCell(3).getText());
            tableRow.put("totalCount", table.getRow(i).getCell(4).getText());
            tableRow.put("amount", table.getRow(i).getCell(4).getText());
            machCheckTable.add(tableRow);
        }
        matchCheck.put("machCheckTable", machCheckTable);

        regex = "详见附表(.*)。";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(paragraphs.get(matchCheckPointer + 1).getText());
        List<LinkedHashMap<String, String>> attachmentList = new ArrayList<>();
        if (matcher.find()) {
            String[] tableName = matcher.group(1).split("、");
            for (int i = 0; i < tableName.length; i++) {
                LinkedHashMap<String, String> tab = new LinkedHashMap<>();
                String reg = "(.*)《(.*)》";
                Pattern pat = Pattern.compile(reg);
                Matcher mat = pat.matcher(tableName[i]);
                if (mat.find()) {
                    tab.put(mat.group(1), mat.group(2));
                }
                attachmentList.add(tab);
            }
            matchCheck.put("attachmentList", attachmentList);
        } else {
            matchCheck.put("attachmentList", null);
        }
        return matchCheck;
    }

    /**
     * @description: 读取基础情况信息
     * @return: java.util.LinkedHashMap<java.lang.String, java.util.LinkedHashMap>
     * @author zhouxs-a
     * @date 2023/11/3 13:01
     */
    private static LinkedHashMap<String, Object> ReadBasicInfo() {
        LinkedHashMap<String, Object> basicInfo = new LinkedHashMap<>();
        if (basicInfoPointer == 0){
            return basicInfo;
        }

        String regex = "项目名称：(.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(paragraphs.get(basicInfoPointer).getText());
        if (matcher.find()) {
            basicInfo.put("name", matcher.group(1));
        } else {
            basicInfo.put("name", null);
        }

        regex = "于(.*?)导入，并于(.*?)生成报告";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(paragraphs.get(basicInfoPointer + 1).getText());
        if (matcher.find()) {
            basicInfo.put("importTime", matcher.group(1));
            basicInfo.put("exportTime", matcher.group(1));
        } else {
            basicInfo.put("importTime", null);
            basicInfo.put("exportTime", null);
        }

        regex = "共检查(.*?)家投标标.*检查(.*?)个部分.*为第(.*?)轮清标";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(paragraphs.get(basicInfoPointer + 2).getText());
        if (matcher.find()) {
            basicInfo.put("tendererCount", matcher.group(1));
            basicInfo.put("checkCount", matcher.group(2));
            basicInfo.put("multiRound", matcher.group(3));
        } else {
            basicInfo.put("tendererCount", null);
            basicInfo.put("checkCount", null);
            basicInfo.put("multiRound", null);
        }

        XWPFTable table = tables.get(0);
        List<LinkedHashMap<String, String>> basicInfoTable = new ArrayList<>();
        for (int i = 1; i < table.getNumberOfRows(); i++) {
            LinkedHashMap<String, String> tableRow = new LinkedHashMap<>();
            String number = table.getRow(i).getCell(0).getParagraphs().get(0).getNumLevelText();
            if ("%1".equals(number)) {
                tableRow.put("tendererNo", String.valueOf(i));
            } else {
                Assert.fail(String.format("文件：%s，基础信息表，序号有误！", docxName));
            }
            tableRow.put("tendererName", table.getRow(i).getCell(1).getText());
            tableRow.put("amount", table.getRow(i).getCell(2).getText());
            basicInfoTable.add(tableRow);
        }
        basicInfo.put("basicInfoTable", basicInfoTable);

        return basicInfo;
    }

    /**
     * @description: 初始化段落指针方法
     * @Param docxPath: docx文件的路径
     * @return: void
     * @author zhouxs-a
     * @date 2023/11/3 15:54
     */
    private static void init(String docxPath) {
        docxName = new File(docxPath).getName();
        try (FileInputStream fis = new FileInputStream(docxPath)) {
            // 打开Word文件
            XWPFDocument docx = new XWPFDocument(fis);
            // 打印doc文本内容的同时获取文本的格式
            paragraphs = docx.getParagraphs();
            tables = docx.getTables();
            for (int i = 1 ; i < paragraphs.size(); i++) {
                // 打印文本内容，不包含表格中的内容
                switch (paragraphs.get(i).getText()) {
                    case BASIC_INFO_TITLE:
                        basicInfoPointer = i + 1;
                        break;
                    case MATCH_CHECK_TITLE:
                        matchCheckPointer = i + 1;
                        break;
                    case CALC_CHECK_TITLE:
                        calcCheckPointer = i + 1;
                        break;
                    case HARDWARE_TITLE:
                        hardwarePointer = i + 1;
                        break;
                    case ATTACHMENT_TITLE:
                    case ATTACHMENT_TITLE_1:
                        attachmentPointer = i + 1;
                        hasHardware = false;
                        break;
                    case SIGNATURE_TITLE_1:
                    case SIGNATURE_TITLE:
                        signaturePointer = i + 1;
                        hasHardware = false;
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail(String.format("文件：%s打开失败！", docxName));
        }
        if (basicInfoPointer == 0) {
            Assert.fail(String.format("文件：%s，缺少内容：%s！", docxName, BASIC_INFO_TITLE));
        }
        if (matchCheckPointer == 0) {
            Assert.fail(String.format("文件：%s，缺少内容：%s！", docxName, MATCH_CHECK_TITLE));
        }
        if (calcCheckPointer == 0) {
            Assert.fail(String.format("文件：%s，缺少内容：%s！", docxName, CALC_CHECK_TITLE));
        }
        if (hardwarePointer == 0 && hasHardware) {
            Assert.fail(String.format("文件：%s，缺少内容：%s！", docxName, HARDWARE_TITLE));
        }
        if (attachmentPointer == 0 && hasHardware) {
            Assert.fail(String.format("文件：%s，缺少内容：%s！", docxName, ATTACHMENT_TITLE));
        }
        if (attachmentPointer == 0 && !hasHardware) {
            Assert.fail(String.format("文件：%s，缺少内容：%s！", docxName, ATTACHMENT_TITLE_1));
        }
        if (signaturePointer == 0 && hasHardware) {
            Assert.fail(String.format("文件：%s，缺少内容：%s！", docxName, SIGNATURE_TITLE));
        }
        if (signaturePointer == 0  && !hasHardware) {
            Assert.fail(String.format("文件：%s，缺少内容：%s！", docxName, SIGNATURE_TITLE_1));
        }
    }
}
