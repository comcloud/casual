package com.kuang.utils;

import lombok.Data;

import java.util.List;

/**
 * @author HP
 */
@Data
public class ModelUtil<T> {
    /**模型对象*/
    private T obj;
    /**模型对象中图片base64数据集合 指上传多张图片*/
    private List<String> base64;
}
