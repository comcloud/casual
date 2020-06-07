package com.kuang.service.impl;

import com.kuang.mapper.UserMapper;
import com.kuang.pojo.User;
import com.kuang.pojo.UserPictures;
import com.kuang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author HP
 * @noinspection ALL
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean update(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public User get(int userId) {
        return userMapper.selectOne(userId);
    }

    @Override
    public boolean update(UserPictures userPictures) {
        return userMapper.updateUserPictures(userPictures);
    }
}
