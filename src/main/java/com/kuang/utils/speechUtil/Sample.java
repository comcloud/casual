package com.kuang.utils.speechUtil;

import com.baidu.aip.speech.AipSpeech;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

public class Sample {
    //设置APPID/AK/SK
    public static final String APP_ID = "19165438";
    public static final String API_KEY = "Ge5FVSRwxMqTFIhtsANtjmAR";
    public static final String SECRET_KEY = "LtwH1D69AVetzvGnNdPmS18MpcPwxFcC";

    private static String result;    //返回的语音结果

    // 初始化一个AipSpeech
    private static AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

    public static String getResult() {
        return result;
    }

    public static void main(String[] args) throws JSONException, InterruptedException, SQLException {

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        //开始实时监测录音
        EngineeCore engineeCore = new EngineeCore();
        engineeCore.main(null);

        // 调用接口
        JSONObject res = client.asr("F:\\SpringBoot学习文件夹_最终\\springboot-05-mybatis\\src\\main\\resources\\resother\\voice\\coop.wav", "wav", 16000, null);
//        System.out.println(res.toString(2));

        String content = res.toString(2);
        //将json字符串转化成json对象
        JsonParser jp = new JsonParser();
        //获取message对应的值
        JsonObject jo = jp.parse(content).getAsJsonObject();

        if(jo.get("result") == null)
            new jacobtest().textToSpeech("对不起");
        else
           result = jo.get("result").getAsString();

        System.out.println(result);
    }

}