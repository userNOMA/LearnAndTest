package com.gvb.glodon;

/**
 * SysLearnPrj
 * 14212
 * 2023.10.05 00:24
 *
 * @author zxs
 * description 执行封装好的测试
 */
public class APITestExe {
    private static final String CASE_PATH = "NatDayDemo\\src\\test\\resources\\APICases.xlsx";
    public static void main(String[] args) {

        // 从excel中读取接口信息
        XlsxReader xlsxReader = new XlsxReader(CASE_PATH);

        // 根据接口信息执行对应的接口
        APITenderOperate APIOpe = new APITenderOperate();
        APIOpe.APIExe(xlsxReader.getXlsData());

    }
}
