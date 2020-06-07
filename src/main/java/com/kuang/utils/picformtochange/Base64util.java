package com.kuang.utils.picformtochange;

import org.apache.commons.codec.binary.Base64;

/**
 * @Author :
 * @Description :
 * @Date : 2020/6/1 9:31
 * @Version ：1.0
 */
public class Base64util {

    /**
     * 图片转为base64格式
     *
     * @param b
     * @return
     */
    public static String byte2Base64StringFun(byte[] b){
        return Base64.encodeBase64String(b);
    }
}
