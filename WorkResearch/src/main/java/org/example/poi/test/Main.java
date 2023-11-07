package org.example.poi.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import org.example.utils.bs_JsonShadow;

/**
 * @author zhouxs-a
 * @version 1.0
 * @description: TODO
 * @date 2023/11/3 16:30
 */
public class Main {
    public static void main(String[] args) {
        String docxPath = "WorkResearch/src/main/resources/清标报告.docx";
        LinkedHashMap<String, Object> clearReport = WordUtils.ReadClearReport(docxPath);
        System.out.println(clearReport);
        System.out.println(JSON.toJSONString(clearReport, SerializerFeature.SortField,  SerializerFeature.PrettyFormat));

        String jsonPath = "WorkResearch/src/main/resources/test.json";
        JSONObject jsonRead;
        try (InputStream is = new FileInputStream(jsonPath)) {
            jsonRead = JSONObject.parseObject(is, JSONObject.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LinkedHashMap<String, Object> linkedHashMap =  bs_JsonShadow.getInstance().fromJson(bs_JsonShadow.getInstance().toJsonString(jsonRead), LinkedHashMap.class);

        String jsonPath2 = "WorkResearch/src/main/resources/test2.json";
        JSONObject jsonRead2;
        try (InputStream is = new FileInputStream(jsonPath2)) {
            jsonRead2 = JSONObject.parseObject(is, JSONObject.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LinkedHashMap<String, Object> linkedHashMap2 =  bs_JsonShadow.getInstance().fromJson(bs_JsonShadow.getInstance().toJsonString(jsonRead2), LinkedHashMap.class);;

        System.out.println(linkedHashMap);

    }
}
