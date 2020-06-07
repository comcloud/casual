package com.kuang.mapper;

import com.kuang.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

/**
 * @Author :
 * @Description :
 * @Date : 2020/5/27 19:59
 * @Version ：1.0
 */
@Mapper
//@Repository
public interface IndexMapper {

    /**
     * @param user_name 用户名
     * @param user_password 密码
     * @return 用户对象
     */
    User getlogin(@Param("user_name") String user_name,
               @Param("user_password") String user_password);


    /**
     * @param user_nickname 用户昵称
     * @param user_name 用户名
     * @param user_password 密码
     *
     */
    void getregister(@Param("user_nickname") String user_nickname, @Param("user_name")String user_name,
                  @Param("user_password") String user_password);

    Collection<Sorts> getall(@Param("parent_id")int parent_id);

    List<Task> gettaskfromname(@Param("sort_name") String sort_name);

    Collection<Sorts> getallsmallsorts(@Param("parent_sort_id")int parent_sort_id);

    List<Task> gettask();

    Task gettaskfromid(@Param("task_id") String task_id);

    Collection<Comments> getcomments(@Param("task_id")String task_id);

    List<TaskPictures> getpic();

    /**
     * 根据用户uuid值获取用户信息
     * @param uuid 查询用户uuid值
     * @return 用户信息
     */
    User selectByUuid(String uuid);

    /**
     * @param user 添加用户
     * @return 添加用户
     */
    Integer insertOne(@Param("user") User user);

    /**
     * @param parseInt 用户userId
     * @return 用户对象信息
     */
    User selectUserByUserId(int parseInt);

    /**
     * @param bigsort_name 大分类名
     * @return 请求集合信息
     */
    Collection<Task> gettaskfrombigsortname(String bigsort_name);

    /**
     * @return 获取大分类集合
     */
    List<String> getbigsortname(int parent_sort_id);

    /**
     * @return 获取小分类结合
     */
    List<String> getsmallsortname(int parent_sort_id);

    /**
     * @param task 请求对象
     * @return 对应请求集合
     */
    List<Task> gettaskfromsearchname(@Param("task") Task task);

    /**
     * @param taskId 请求id
     * @return 请求图片集合
     */
    List<TaskPictures> getalltaskPic(String taskId);

    /**
     * @param userId 用户id
     * @param task_id 发布id
     * @param commentTime 发布时间
     * @param commenttext 发布内容
     */
    void setcomment(long userId, String task_id, Timestamp commentTime, String commenttext);

    void updatecomment(long commentId);

    void addtasklikecount(@Param("task_id") String task_id);

    void addcommentformid(@Param("comments") Comments comments);

}
