package com.kuang.mapper;

import com.kuang.pojo.User;
import com.kuang.pojo.UserPictures;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author HP
 */
@Mapper
public interface UserMapper {
    /**
     * 更新一个用户
     * @param user 插入用户
     */
    boolean updateUser(@Param("user") User user);

    /**
     * @param userId 用户id
     * @return 用户信息
     */
    User selectOne(@Param("userId") int userId);

    boolean updateUserPictures(@Param("userPictures") UserPictures userPictures);
}
