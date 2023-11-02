package com.gvb.glodon;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SysLearnPrj
 * 14212
 * 2023.10.04 21:53
 *
 * @author zxs
 * description 招投标接口操作
 */
public class APITenderOperate {

    private static OkHttpGVB okHttpGVB = new OkHttpGVB();
    private int bidSegId;
    private String id;

    /**
     * 按照xlsx中的信息执行接口请求
     * @param xlsxReaderList 读取的请求对象
     */
    public void APIExe(List<XlsxReader> xlsxReaderList){

        for (XlsxReader APIInfo : xlsxReaderList) {

            String result = "";
            switch (APIInfo.getApiName()) {
                case "uploadTender":
                    result = uploadTender(APIInfo.getUrl(), APIInfo.getHeader(), APIInfo.getBody());
                    System.out.println("上传招标结果：");
                    System.out.println(result);
                    this.bidSegId = getBidSegId(result);
                    break;
                case "uploadTenderee":
                    result = uploadTenderee(APIInfo.getUrl(), APIInfo.getHeader(), APIInfo.getBody());
                    System.out.println("上传投标结果：");
                    System.out.println(result);
                    break;
                case "readTenders":
                    result = readTenders(APIInfo.getUrl()+(bidSegId-2), APIInfo.getHeader());
                    System.out.println("查看投标结果：");
                    System.out.println(result);
                    this.id = getId(result);
                    break;
                case "deleteTenderee":
                    result = deleteTenderee(APIInfo.getUrl(), APIInfo.getHeader(), APIInfo.getBody());
                    System.out.println("删除投标结果：");
                    System.out.println(result);
                    break;
            }
        }
    }

    /**
     * 上传招标文件接口
     * @param url 请求地址
     * @param header 请求头
     * @param body 请求参数
     * @return 返回字符串
     */
    public String uploadTender(String url, String header, String body){
        Map<String, Object> headerMap = JSON.parseObject(header, new TypeReference<Map<String, Object>>() {});
        BodyInv fileData = JSON.parseObject(body, BodyInv.class);
        return okHttpGVB.doPost(url, headerMap, JSON.toJSONString(fileData));
    }

    /**
     * 上传投标文件接口
     * @param url 请求地址
     * @param header 请求头
     * @param body 请求参数
     * @return 返回字符串
     */
    public String uploadTenderee(String url, String header, String body){
        Map<String, Object> headerMap = JSON.parseObject(header, new TypeReference<Map<String, Object>>() {});
        BodySub fileData = JSON.parseObject(body, BodySub.class);
        if (bidSegId != 0){
            fileData.setBidSegId(String.valueOf(bidSegId));
        }
        return okHttpGVB.doPost(url, headerMap, JSON.toJSONString(fileData));
    }

    /**
     * 读取投标列表接口
     * @param url 请求地址
     * @param header 请求头
     * @return 返回字符串
     */
    public String readTenders(String url, String header){
        Map<String, String> headerMap = JSON.parseObject(header, new TypeReference<Map<String, String>>() {});
        return okHttpGVB.doGet(url, headerMap);
    }

    /**
     * 删除投标接口
     * @param url 请求地址
     * @param header 请求头
     * @param body 请求参数
     * @return 返回字符串
     */
    public String deleteTenderee(String url, String header, String body){
        Map<String, Object> headerMap = JSON.parseObject(header, new TypeReference<Map<String, Object>>() {});
        BodySubDel fileData = JSON.parseObject(body, BodySubDel.class);
        if (id != null){
            fileData.setId(id);
        }
        return okHttpGVB.doPost(url, headerMap, JSON.toJSONString(fileData));
    }

    private int getBidSegId(String result){
        Map json = JSON.parseObject(result);
        Map data = (Map) json.get("data");
        return (int) data.get("bidSegId");
    }

    private String getId(String result){
        Map json = JSON.parseObject(result);
        List data = (List) json.get("data");
        Map dat = (Map) data.get(data.size()-1);
        return (String) dat.get("id");
    }
}
