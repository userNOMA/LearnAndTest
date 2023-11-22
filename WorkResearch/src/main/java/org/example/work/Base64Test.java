package org.example.work;

import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouxs-a
 * @version 1.0
 * @description: TODO
 * @date 2023/11/13 23:14
 */
public class Base64Test {

    public static void main(String[] args) {
        byte[] decodedBytes = Base64.getDecoder().decode("N0MtNTctNTgtQzItNEMtMTQ=");
        String decodedString = new String(decodedBytes);
        System.out.println(decodedString);

        // 对于山东地区而言，dogId和macAddr要进行Base64解密处理
//        if ("BeiJing_XML_03".equals(dataUnit.get("interFaceCode"))) {
//            for (Map.Entry file : HardwareExpTotal.entrySet()) {
//                LinkedHashMap valueFile = (LinkedHashMap) file.getValue();
//                List ruleValue = (List) valueFile.get("软硬件检查");
//                for (int i = 0; i < ruleValue.size(); i++) {
//                    LinkedHashMap<String, String> keyRow = (LinkedHashMap) ruleValue.get(i);
//                    for (Map.Entry key : keyRow.entrySet()) {
//                        if ("dogId".equals(key.getKey())) {
//                            byte[] decodedBytes = Base64.getDecoder().decode(key.getValue().toString());
//                            String decodedString = new String(decodedBytes);
//                            key.setValue(decodedString);
//
//                        }
//                        if ("macAddr".equals(key.getKey())) {
//                            byte[] decodedBytes = Base64.getDecoder().decode(key.getValue().toString());
//                            String decodedString = new String(decodedBytes);
//                            key.setValue(decodedString);
//                        }
//                    }
//                }
//            }
//        }
    }
}
