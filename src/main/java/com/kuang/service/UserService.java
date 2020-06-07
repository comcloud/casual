package com.kuang.service;

import com.kuang.pojo.User;
import com.kuang.pojo.UserPictures;

/**
 * @author HP
 */
public interface UserService {
    /**
     * 保存用户信息
     * @param user 更新的用户信息
     */
    boolean update(User user);

    /**
     * 根据用户id获取用户信息
     * @param userId 用户Id
     * @return 用户信息
     */
    User get(int userId);

    boolean update(UserPictures userPictures);
}
