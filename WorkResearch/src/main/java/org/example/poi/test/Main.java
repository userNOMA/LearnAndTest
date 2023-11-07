package org.example.poi.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.LinkedHashMap;

/**
 * @author zhouxs-a
 * @version 1.0
 * @description: TODO
 * @date 2023/11/3 16:30
 */
public class Main {
    public static void main(String[] args) {
        String docxPath = "WorkResearch/src/main/resources/清标报告1.docx";
        LinkedHashMap<String, Object> clearReport = WordUtils.ReadClearReport(docxPath);
        System.out.println(clearReport);
        System.out.println(JSON.toJSONString(clearReport, SerializerFeature.SortField,  SerializerFeature.PrettyFormat));


    }
}
