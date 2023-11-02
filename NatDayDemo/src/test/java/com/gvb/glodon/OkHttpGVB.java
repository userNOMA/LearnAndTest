package com.gvb.glodon;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public class OkHttpGVB {
    public static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");
    public static OkHttpClient okHttpClient = new OkHttpClient();

    public String doPost(String url, Map header, String body) {

        // 将Map转换为Header对象
        Headers headers = Headers.of(header);

        //post方式提交的数据
        RequestBody requestBody = RequestBody.create(JSON_TYPE, body);
        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(requestBody)
                .build();

        // 使用client去请求
        Call call = okHttpClient.newCall(request);


        // 返回结果字符串

        String result = null;
        try {
            // 获得返回结果
            result = call.execute()
                    .body()
                    .string();
        } catch (IOException e) {
            // 抓取异常
            System.out.println("request " + url + " error . ");
            e.printStackTrace();
        }

        return result;
    }

    public String doGet(String url, Map<String, String> headeryMap) {

        // 将Map转换为Header对象
        Headers headers = Headers.of(headeryMap);

        // 定义request组装请求头
        Request request = new Request.Builder()
                .headers(headers)
                .url(url)
                .build();

        // 使用client去请求
        Call call = okHttpClient.newCall(request);

        // 返回结果字符串
        String result = null;

        try {
            // 获得返回结果
            result = call.execute().body().string();
            //这里的call也经常写成response，其中response为Response response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            // 抓取异常
            System.out.println("request " + url + " error . ");
            e.printStackTrace();
        }
        return result;
    }
}
