package com.kuang.pojo;

import lombok.*;

/**
 * 用户图片类，用于存放用户喜欢的图片
 * @author HP
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UserPictures {
    private int userId;
    private byte[] userPictures;
}
