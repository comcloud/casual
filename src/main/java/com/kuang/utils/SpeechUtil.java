package com.kuang.utils;

import com.kuang.controller.fore.IndexController;
import com.kuang.utils.speechUtil.Sample;
import com.kuang.utils.speechUtil.jacobtest;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @Author :
 * @Description :
 * @Date : 2020/6/3 1:51
 * @Version ：1.0
 */
public class SpeechUtil {
    private static String regetResultBig = null;
    private static String regetResultSmall = null;
    public static String getRegetResultBig() {
        return regetResultBig;
    }

    public static String getRegetResultSmall() {
        return regetResultSmall;
    }

    public static void setRegetResultBig(String regetResultBig) {
        SpeechUtil.regetResultBig = regetResultBig;
    }

    public static void setRegetResultSmall(String regetResultSmall) {
        SpeechUtil.regetResultSmall = regetResultSmall;
    }

    public static String speechutil(){
        if(Sample.getResult() == null){
            new jacobtest().textToSpeech("我没听清您说的话");
            return null;
        }

        if(Sample.getResult().contains("bbox")||Sample.getResult().contains("box")||
                Sample.getResult().contains("bboss")||Sample.getResult().contains("boss")){
            new jacobtest().textToSpeech("哎呀！我不会，不过没关系，嘿Siri，来一段beatbox");
            return null;
        }else if(Sample.getResult().contains("放个屁")){
            new jacobtest().textToSpeech("噗噗噗噗，噗噗噗");
            return null;
        }else if(Sample.getResult().contains("我帅吗")||Sample.getResult().contains("帅吗")){
            new jacobtest().textToSpeech("那还用说！是个人的话都能看见您的魅力");
            return null;
        }else if(Sample.getResult().contains("冷笑话")||Sample.getResult().contains("笑话")
                ||Sample.getResult().contains("讲笑话")||Sample.getResult().contains("讲冷笑话")
                ||Sample.getResult().contains("讲一个笑话")||Sample.getResult().contains("将个笑话")
                ||Sample.getResult().contains("讲个冷笑话")){
            new jacobtest().textToSpeech("一只蛐蛐跟猪打赌说：我跳进草里你就听不见我的声音，" +
                    "猪说：我要听得见呢? 于是蛐蛐跳进草里。猪在听，猪在听!猪还在听!猪咋还在听呢!");
            return null;
        }else if(Sample.getResult().contains("你会什么")||Sample.getResult().contains("你的技能")||
                Sample.getResult().contains("你有什么技能")||Sample.getResult().contains("技能")
                ||Sample.getResult().contains("你的功能")||Sample.getResult().contains("功能")
                ||Sample.getResult().contains("作用")||Sample.getResult().contains("你的作用")
                ||Sample.getResult().contains("你都会什么")){
            new jacobtest().textToSpeech("我可厉害了！你可以通过我打开QQ,优酷视频,播放音乐等应用程序");
            new jacobtest().textToSpeech("您现在可以尝试一下");
            SpeechAgain();
            return null;
        }else if(Sample.getResult().contains("打开优酷")|| Sample.getResult().contains("优酷") ||
                Sample.getResult().contains("优酷应用")){
            try {
                Process p = java.lang.Runtime.getRuntime().exec("D:\\Program Files (x86)\\YouKu\\YoukuClient\\proxy\\YoukuDesktop.exe");
                System.err.println("p===="+p.getInputStream());

            } catch (IOException ex1)
            {
                new jacobtest().textToSpeech("请检查您的优酷路径是否正确后再尝试");
            }
            return null;
        }else if(Sample.getResult().contains("打开QQ")|| Sample.getResult().contains("QQ") ||
                Sample.getResult().contains("QQ应用")){
            try {

                Process p = java.lang.Runtime.getRuntime().exec("C:\\Program Files\\tencent\\QQ\\Bin\\QQ.exe");
                System.err.println("p===="+p.getInputStream());

            } catch (IOException ex1)
            {
                new jacobtest().textToSpeech("请检查您的QQ路径是否正确后再尝试");
            }
        }
        else{
            if(regetResultBig != null || regetResultSmall != null){
                return "1";
            }else {
                new jacobtest().textToSpeech("对不起！我好像没有听懂您的意思");
                return null;
            }
        }
        return null;
    }

    //去除字符串中的标点
    public static String format(String s){
        String str=s.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
        return str;
    }

    //再次录音识别
    public static void SpeechAgain(){
        try {
           Sample.main(null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(Sample.getResult().contains("打开优酷")|| Sample.getResult().contains("优酷") ||
                Sample.getResult().contains("优酷视频")){
            try {
                Process p = java.lang.Runtime.getRuntime().exec("D:\\Program Files (x86)\\YouKu\\YoukuClient\\proxy\\YoukuDesktop.exe");
                System.err.println("p===="+p.getInputStream());

            } catch (IOException ex1)
            {
                new jacobtest().textToSpeech("请检查您的优酷路径是否正确后再尝试");
            }
        }else if(Sample.getResult().contains("打开QQ")|| Sample.getResult().contains("QQ") ||
                Sample.getResult().contains("QQ应用")){
            try {

                Process p = java.lang.Runtime.getRuntime().exec("C:\\Program Files\\tencent\\QQ\\Bin\\QQ.exe");
                System.err.println("p===="+p.getInputStream());

            } catch (IOException ex1)
            {
                new jacobtest().textToSpeech("请检查您的QQ路径是否正确后再尝试");

            }
        }else{
            new jacobtest().textToSpeech("对不起，我好像没有听懂您的意思");
        }

    }
}
