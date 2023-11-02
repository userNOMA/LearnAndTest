package com.gvb.glodon;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestOkHttpTools {

    public static void main(String[] args) {

        OkHttpGVB okHttpGVB = new OkHttpGVB();

        // 接口1
        String url = "http://10.0.172.98:7071/vbp/build/bidSeg";

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer cn-92ced2e8-1b62-474a-b5ee-e1ad23c147c3-ut");

        BodyInv bodyInv = new BodyInv();
        bodyInv.setFileId("vbp-test/BeiJingTenderFile/VBP_TEST/20230928134903/北京效率测试-招标1000清单.xml");
        bodyInv.setFileType("XML");
        bodyInv.setFileName("北京效率测试-招标1000清单.xml");
        bodyInv.setFileSize("844251");
        bodyInv.setConfigType("Tenderee");
        bodyInv.setIsTender("1");
        String body = JSON.toJSONString(bodyInv);
        System.out.println(body);

        String content = okHttpGVB.doPost(url, header, body);

        System.out.println("接口1调用结果：");
        System.out.println(content);

        // 接口2
        url = "http://10.0.172.98:7071/vbp/tender/tenderer";

        BodySubFile subFile1 = new BodySubFile();
        subFile1.setFileId("vbp-test/BeiJingTenderFile/VBP_TEST/20230928135632/北京效率测试-投标1000清单-50%单价相同.xml");
        subFile1.setFileName("北京效率测试-投标1000清单-50%单价相同.xml");
        subFile1.setFileSize("19350880");
        subFile1.setFilePath("");

        BodySubFile subFile2 = new BodySubFile();
        subFile2.setFileId("vbp-test/BeiJingTenderFile/VBP_TEST/20230928135632/北京效率测试-投标1000清单-23%单价相同.xml");
        subFile2.setFileName("北京效率测试-投标1000清单-23%单价相同.xml");
        subFile2.setFileSize("19499739");
        subFile2.setFilePath("");

        List<BodySubFile> bodySubFiles = new ArrayList<>();
        bodySubFiles.add(subFile1);
        bodySubFiles.add(subFile2);

        BodySub bodySub = new BodySub();
        bodySub.setBidSegId("26803");
        bodySub.setFileId(bodySubFiles);
        bodySub.setFileType("XML");
        bodySub.setInterfaceCode("BeiJing_XML_03");

        body = JSON.toJSONString(bodySub);

        content = okHttpGVB.doPost(url, header, body);

        System.out.println("接口2调用结果：");
        System.out.println(content);

        // 接口3
        url = "http://10.0.172.98:7071/vbp/tender/getTenderInfoList/26803";

        content = okHttpGVB.doGet(url, header);

        System.out.println("接口3调用结果：");
        System.out.println(content);

        //接口4
        url = "http://10.0.172.98:7071/vbp/tender/deleteTenderFile";

        BodySubDel bodySubDel = new BodySubDel();
        bodySubDel.setId("1709560483554492416");
        bodySubDel.setCollectionName("tender_data");
        body = JSON.toJSONString(bodySubDel);

        content = okHttpGVB.doPost(url, header, body);

        System.out.println("接口4调用结果：");
        System.out.println(content);


    }
}
