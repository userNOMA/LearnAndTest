package org.example.poi.test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.example.utils.ZXSUtils;
import org.example.utils.bs_JsonShadow;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.example.poi.test.TestReadJsonForReport.readJsonForReport;

/**
 * @author zhouxs-a
 * @version 1.0
 * @description: TODO
 * @date 2023/11/9 22:16
 */
public class TestCompare {
    public static void main(String[] args) {
        String testJsonPath = "WorkResearch/src/main/resources/work/TestReadJsonForReport.json";
        JSONObject jsonRead;
        try (InputStream is = new FileInputStream(testJsonPath)) {
            jsonRead = JSONObject.parseObject(is, JSONObject.class, Feature.OrderedField);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LinkedHashMap<String, Object> apiResult = bs_JsonShadow.getInstance().fromJson(bs_JsonShadow.getInstance().toJsonString(jsonRead), LinkedHashMap.class);
        List<LinkedHashMap> tenderInfoList = (List) apiResult.get("tenderInfoList");
        LinkedHashMap<String, Object> clearResult = (LinkedHashMap<String, Object>) apiResult.get("clearResult");
        LinkedHashMap<String, Object> matchResult = (LinkedHashMap<String, Object>) apiResult.get("matchResult");
        List<LinkedHashMap> hardwareDetail = (List) apiResult.get("hardwareDetail");
        List<LinkedHashMap> reportMenu = (List) apiResult.get("reportMenu");

        LinkedHashMap<String, Object> clearReportExp = readJsonForReport(tenderInfoList, clearResult, matchResult, reportMenu, hardwareDetail);


        String docxPath = "WorkResearch/src/main/resources/work/清标报告-全.docx";
        LinkedHashMap<String, Object> clearReportAct = WordUtils.ReadClearReport(docxPath);

        System.out.println(clearReportExp.get("basicInfo:清标信息"));
        System.out.println(clearReportAct.get("basicInfo:清标信息"));

        String err = "\n--------【No:%s-%s】%s:%s--------%s";
        err = String.format(err,"1","生成报表","%s","页面与期望不符","%s");
        List<String> errTotal = new ArrayList<>();
        errTotal = ZXSUtils.getClearReportCompareResults(clearReportExp, clearReportAct, err, errTotal);
        if (errTotal.size() > 0) {
            Assert.fail(String.join("", errTotal));
        }
    }
}
