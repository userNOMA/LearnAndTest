package org.example.poi.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import org.example.utils.bs_JsonShadow;
import org.example.utils.bs_JacksonHelper;

/**
 * @author zhouxs-a
 * @version 1.0
 * @description: 测试将接口返回的JSON数据进行拼接的方法
 * @date 2023/11/8 18:16
 */
public class TestReadJsonForReport {

    public static void main(String[] args) {
        String testJsonPath = "WorkResearch/src/main/resources/work/TestReadJsonForReport.json";
        JSONObject jsonRead;
        try (InputStream is = new FileInputStream(testJsonPath)) {
            jsonRead = JSONObject.parseObject(is, JSONObject.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LinkedHashMap<String, Object> apiResult = bs_JsonShadow.getInstance().fromJson(bs_JsonShadow.getInstance().toJsonString(jsonRead), LinkedHashMap.class);
        List<LinkedHashMap> tenderInfoList = (List) apiResult.get("tenderInfoList");
        LinkedHashMap<String, Object> clearResult = (LinkedHashMap<String, Object>) apiResult.get("clearResult");
        LinkedHashMap<String, Object> matchResult = (LinkedHashMap<String, Object>) apiResult.get("matchResult");
        List<LinkedHashMap> hardwareDetail = (List) apiResult.get("hardwareDetail");
        List<LinkedHashMap> reportMenu = (List) apiResult.get("reportMenu");

        readJsonForReport(tenderInfoList, clearResult, matchResult, reportMenu, hardwareDetail);
    }

    public static LinkedHashMap<String, Object> readJsonForReport(List<LinkedHashMap> tenderInfoList,
                                                                  LinkedHashMap<String, Object> clearResult,
                                                                  LinkedHashMap<String, Object> matchResult,
                                                                  List<LinkedHashMap> reportMenu,
                                                                  List<LinkedHashMap> hardwareDetail) {
        LinkedHashMap<String, Object> reportJson = new LinkedHashMap<>();

        // 从投标信息列表读取信息
        LinkedHashMap<String, Object> basicInfo = new LinkedHashMap<>();
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        List<LinkedHashMap<String, String>> basicInfoTable = new ArrayList<>();
        for (int i = 0; i < tenderInfoList.size(); i++) {
            LinkedHashMap<String, String> tableRow = new LinkedHashMap<>();
            tableRow.put("tendererNo:序号", String.valueOf(i+1));
            tableRow.put("tendererName:单位名称", tenderInfoList.get(i).get("tenderer").toString());
            tableRow.put("amount:总报价（元）", decimalFormat.format(tenderInfoList.get(i).get("amount")));
            basicInfoTable.add(tableRow);
        }
        reportJson.put("basicInfoTable:标书情况统计表", basicInfoTable);

        // 从清标结果汇总中读取信息
        LinkedHashMap<String, Object> matchCheck = new LinkedHashMap<>();
        LinkedHashMap<String, Object> calcCheck = new LinkedHashMap<>();
        LinkedHashMap<String, Object> hardwareCheck = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日HH时mm分");
        LinkedHashMap<String, Object> resultReport = (LinkedHashMap) clearResult.get("resultReport");
        basicInfo.put("tendererCount:标书数量", resultReport.get("tendererCount"));
        basicInfo.put("name:项目名称", resultReport.get("bidsegName"));
        Instant instant = Instant.ofEpochMilli(((long) resultReport.get("createTime")));
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        basicInfo.put("importTime:导入时间", localDateTime.format(formatter));
        basicInfo.put("validBidRound:报告轮次", resultReport.get("validBidRound"));
        // TODO 目前使用固定值3，后续需要根据是否存在软硬件信息进行判断
        basicInfo.put("checkCount:检查部分数量", "3");
        // 计算符合性检查一致和不一致的投标单位数量，以及统计结果
        List<LinkedHashMap> tenderInfos = (List<LinkedHashMap>) clearResult.get("tenderInfos");
        List<LinkedHashMap> tableData = (List<LinkedHashMap>) clearResult.get("tableData");
        List<String> tenderId = new ArrayList<>();
        List<String> tenderName = new ArrayList<>();
        for (int i = 0; i < tableData.size(); i++) {
            LinkedHashMap<String, Object> rowData = tableData.get(i);
            if ("符合性检查".equals(rowData.get("checkItem"))) {
                int differenceTendererCount = 0;
                for (int j = 0; j < tenderInfos.size(); j++) {
                    LinkedHashMap<String, String> infoRow = tenderInfos.get(j);
                    tenderId.add(infoRow.get("prop"));
                    tenderId.add(tenderInfos.get(j).get("label").toString());
                    if (!"0".equals(rowData.get(infoRow.get("prop")))) {
                        differenceTendererCount++;
                    }
                }
                int sameTendererCount = tenderInfos.size() - differenceTendererCount;
                matchCheck.put("differenceTendererCount:符合性不一致单位数量", String.valueOf(differenceTendererCount));
                matchCheck.put("sameTendererCount:符合性一致单位数量", String.valueOf(sameTendererCount));
            }
            List<LinkedHashMap<String, String>> calcCheckTable = new ArrayList<>();
            if ("计算性检查".equals(rowData.get("checkItem"))) {
                int differenceTendererCount = 0;
                for (int j = 0; j < tenderInfos.size(); j++) {
                    LinkedHashMap<String, String> infoRow = tenderInfos.get(j);
                    LinkedHashMap<String, String> tableRow = new LinkedHashMap<>();
                    tableRow.put("tendererNo:序号", String.valueOf(j+1));
                    tableRow.put("tendererName:单位名称", tenderInfos.get(j).get("label").toString());
                    tableRow.put("errorItemCount:错误数量", rowData.get(infoRow.get("prop")).toString());
                    if (!"0".equals(rowData.get(infoRow.get("prop")))) {
                        differenceTendererCount++;
                    }
                    calcCheckTable.add(tableRow);
                }
                int sameTendererCount = tenderInfos.size() - differenceTendererCount;
                calcCheck.put("errorTendererCount:计算性错误单位数量", String.valueOf(differenceTendererCount));
                calcCheck.put("correctTendererCount:计算性正确单位数量", String.valueOf(sameTendererCount));
                calcCheck.put("calcCheckTable:计算性统计表", calcCheckTable);
            }
        }

        // 从报表菜单读取拥有哪些符合性检查附表
        LinkedHashMap<String, String> attachment1 = new LinkedHashMap<>();
        attachment1.put("attachmentNo:附件编号", "1");
        attachment1.put("attachmentName:附件名称", "清标结果_清标结果汇总_清标结果汇总表");
        int attachmentNo = 2;
        List<LinkedHashMap<String, String>> attachmentList = new ArrayList<>();
        List<LinkedHashMap<String, String>> attachmentListAll = new ArrayList<>();
        attachmentListAll.add(attachment1);
        for (int i = 0; i < reportMenu.size(); i++) {
            LinkedHashMap<String, String> menu = reportMenu.get(i);
            if (menu.get("checkItemCode").contains("Match")) {
                LinkedHashMap<String, String> attachment = new LinkedHashMap<>();
                if ("MatchCheck".equals(menu.get("checkItemCode"))){
                    attachment.put("attachmentNo:附件编号", String.valueOf(attachmentNo++));
                    attachment.put("attachmentName:附件名称", "清标结果_符合性检查结果_符合性检查结果汇总表");
                } else {
                    attachment.put("attachmentNo:附件编号", String.valueOf(attachmentNo++));
                    attachment.put("attachmentName:附件名称", "清标结果_符合性检查结果_" + menu.get("checkItem") +"表");
                }
                attachmentList.add(attachment);
                attachmentListAll.add(attachment);
            }
        }
        matchCheck.put("attachmentList:符合性附件", attachmentList);
        // 从报表菜单读取拥有哪些计算性检查附表
        attachmentList = new ArrayList<>();
        for (int i = 0; i < reportMenu.size(); i++) {
            LinkedHashMap<String, String> menu = reportMenu.get(i);
            if (menu.get("checkItemCode").contains("Calc")) {
                LinkedHashMap<String, String> attachment = new LinkedHashMap<>();
                if ("CalcCheck".equals(menu.get("checkItemCode"))){
                    attachment.put("attachmentNo:附件编号", String.valueOf(attachmentNo++));
                    attachment.put("attachmentName:附件名称", "清标结果_计算性检查结果_计算性检查结果汇总表");
                } else {
                    attachment.put("attachmentNo:附件编号", String.valueOf(attachmentNo++));
                    attachment.put("attachmentName:附件名称", "清标结果_计算性检查结果_" + menu.get("checkItem"));
                }
                attachmentList.add(attachment);
                attachmentListAll.add(attachment);
            }
        }
        calcCheck.put("attachmentList:计算性附件", attachmentList);

        // 从符合性结果详情读取符合性结果表
        List<LinkedHashMap<String, String>> machCheckTable = new ArrayList<>();
        for (int i = 0; i < tenderId.size(); i++) {
            LinkedHashMap<String, String> tableRow = new LinkedHashMap<>();
            tableRow.put("tendererNo:序号", String.valueOf(i+1));
            tableRow.put("tendererName:单位名称", tenderName.get(i));
            tableRow.put("addItemCount:增项", ((LinkedHashMap) matchResult.get(tenderId.get(i))).get("addItemCount").toString());
            tableRow.put("deleteItemCount:缺项", ((LinkedHashMap) matchResult.get(tenderId.get(i))).get("deleteItemCount").toString());
            tableRow.put("errorCount:错项", ((LinkedHashMap) matchResult.get(tenderId.get(i))).get("errorItemCount").toString());
            tableRow.put("totalCount:合计", ((LinkedHashMap) matchResult.get(tenderId.get(i))).get("totalCount").toString());
            machCheckTable.add(tableRow);
        }
        matchCheck.put("machCheckTable:符合性统计表", machCheckTable);

        // 从软硬件详情读取软硬件信息
        // 将所有的锁号和相同的投标文件名拿出来，




        reportJson.put("basicInfo:清标信息", basicInfo);
        reportJson.put("matchCheckResult:符合性检查结果", matchCheck);
        reportJson.put("calcCheckResult:计算性检查结果", calcCheck);
        reportJson.put("attachment:附件", attachmentListAll);
        // 从投标列表中解析以下结果
        System.out.println(reportJson);

        return reportJson;
    }
}
