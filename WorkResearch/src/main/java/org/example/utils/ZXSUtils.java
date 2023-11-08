package org.example.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author zhouxs-a
 * @version 1.0
 * @description: 备份一些方法
 * @date 2023/11/8 18:03
 */
public class ZXSUtils {


    /**
     * @description: 递归对比清标报告的实际结果和期望结果
     * @Param expTotal: 期望结果
     * @Param actTotal: 实际结果
     * @Param err: 错误显示格式
     * @Param errTotal: 存储全部错误
     * @return: java.util.List<java.lang.String>
     * @author zhouxs-a
     * @date 2023/11/8 17:55
     */
    public static List<String> getClearReportCompareResults(LinkedHashMap<String, Object> expTotal, LinkedHashMap<String, Object> actTotal, String err, List<String> errTotal) {
        // 遍历键
        for (String expKey : expTotal.keySet()) {
            String errKey = expKey.split(":")[1];
            // 排除期望空值，如果期望是没有内容，使用""
            if (expTotal.get(expKey) == null) {
                continue;
//                return errTotal;
            }
            // 确认实际值是否存在
            if (actTotal.get(expKey) == null) {
                String detail = String.format("\n"+"实际：不存在%s部分\n"+"期望：存在%s部分\n", expKey, expKey);
//                Assert.fail(String.format(err,errKey,detail));
                errTotal.add(String.format(err,errKey,detail));
//                return errTotal;
                continue;
            }
            // 值为字符串直接对比
            if (actTotal.get(expKey) instanceof String && expTotal.get(expKey) instanceof String) {
                if (!expTotal.get(expKey).equals(actTotal.get(expKey))) {
                    String detail = String.format("\n"+"实际：%s\n"+"期望：%s\n", actTotal.get(expKey), expTotal.get(expKey));
//                    Assert.fail(String.format(err,errKey,detail));
                    errTotal.add(String.format(err,errKey,detail));
//                    return errTotal;
                }
                continue;
            }
            // 值为布尔也直接对比
            if (actTotal.get(expKey) instanceof Boolean && expTotal.get(expKey) instanceof Boolean) {
                if (!expTotal.get(expKey).equals(actTotal.get(expKey))) {
                    String detail = String.format("\n"+"实际：%s\n"+"期望：%s\n", actTotal.get(expKey).toString(), expTotal.get(expKey).toString());
//                    Assert.fail(String.format(err,errKey,detail));
                    errTotal.add(String.format(err,errKey,detail));
//                    return errTotal;
                }
                continue;
            }
            // 值为列表需要进行排序再对比
            if (actTotal.get(expKey) instanceof List && expTotal.get(expKey) instanceof List) {
                // 软硬件信息可能要特殊处理，但是暂时不考虑
                if (expKey.contains("----hardware----")) {
                    System.out.println("----hardware----");
                }
                else {
                    List expList = (List) expTotal.get(expKey);
                    List actList = (List) actTotal.get(expKey);
                    if (expList.size() != actList.size()) {
                        String detail = String.format("\n"+"实际：%s\n"+"期望：%s\n", "包含"+expList.size()+"条数据", "包含"+actList.size()+"条数据");
//                        Assert.fail(String.format(err,errKey,detail));
                        errTotal.add(String.format(err,errKey,detail));
//                        return errTotal;
                        continue;
                    }
                    List<String> expStringList = new ArrayList<>();
                    List<String> actStringList = new ArrayList<>();
                    // 拼接字符串
                    for (Object expObj: expList) {
                        LinkedHashMap<String, String> expMap = (LinkedHashMap) expObj;
                        StringBuilder resultBuilder = new StringBuilder();
                        expMap.forEach((key, value) -> resultBuilder.append(key.split(":")[1]).append(":").append(value).append(","));
                        expStringList.add(resultBuilder.toString());
                    }
                    for (Object actObj: actList) {
                        LinkedHashMap<String, String> actMap = (LinkedHashMap) actObj;
                        StringBuilder resultBuilder = new StringBuilder();
                        actMap.forEach((key, value) -> resultBuilder.append(key.split(":")[1]).append(":").append(value).append(","));
                        actStringList.add(resultBuilder.toString());
                    }
                    // 对比
                    for (String expString: expStringList) {
                        if (!actStringList.contains(expString)) {
                            String detail = String.format("\n"+"实际：%s\n"+"期望：%s\n", "", expString);
//                            Assert.fail(String.format(err,errKey,detail));
                            errTotal.add(String.format(err,errKey,detail));
//                            return errTotal;
                        }
                    }
                }
            }
            // 值为LinkedHashMap需要回归
            if (actTotal.get(expKey) instanceof LinkedHashMap && expTotal.get(expKey) instanceof LinkedHashMap) {
                LinkedHashMap<String, Object> expTotalChild = (LinkedHashMap<String, Object>) expTotal.get(expKey);
                LinkedHashMap<String, Object> actTotalChild = (LinkedHashMap<String, Object>) actTotal.get(expKey);
//                err = String.format(err, errKey + "-%s", "%s");
                getClearReportCompareResults(expTotalChild, actTotalChild, String.format(err, errKey + "-%s", "%s"), errTotal);
            }
        }
        return errTotal;
    }
}
