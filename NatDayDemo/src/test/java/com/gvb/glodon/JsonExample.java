package com.gvb.glodon;

/**
 * SysLearnPrj
 * 14212
 * 2023.10.05 00:08
 *
 * @author zxs
 * description
 */
import com.alibaba.fastjson.JSON;

public class JsonExample {
    public static void main(String[] args) {
        String json = "{" +
                "\"fileId\":\"vbp-test/BeiJingTenderFile/VBP_TEST/20230928134903/北京效率测试-招标1000清单.xml\"," +
                "\"fileType\":\"XML\"," +
                "\"fileName\":\"北京效率测试-招标1000清单.xml\"," +
                "\"fileSize\":844251," +
                "\"configType\":\"Tenderee\"," +
                "\"bidSegId\":null," +
                "\"isTender\":1" +
                "}";

        // 将JSON字符串转换为Java对象
        BodyInv fileData = JSON.parseObject(json, BodyInv.class);

        // 访问Java对象的属性
        System.out.println("File ID: " + fileData.getFileId());
        System.out.println("File Type: " + fileData.getFileType());

        // 将Java对象转换为JSON字符串
        String convertedJson = JSON.toJSONString(fileData);
        System.out.println("Converted JSON: " + convertedJson);
    }
}

