package com.kuang.service;

import com.kuang.pojo.*;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @Author :
 * @Description :
 * @Date : 2020/5/27 19:58
 * @Version ：1.0
 */
public interface IndexService {

    User login(String user_name, String user_password);

    void register(String user_nickname, String user_name, String user_password);

    Collection<Sorts> getAll(int parent_id);

    List<Task> getTaskFromName(String sort_name);

    Collection<Sorts> getAllSmallSorts(int parent_sort_id);

    List<Task> getTaskAll();

    Task gettaskfromid(String task_id);

    Collection<Comments> getComments(String task_id);

    List<TaskPictures> getPicture();

    /**
     * @param uuid 用户人脸登录的uuid值
     * @return 用户对象
     */
    User get(String uuid);

    /**
     * @param user 用户对象
     * @return 成功与否判断
     */
    boolean add(User user);

    /**
     * @param parseInt 用户userId
     * @return 用户对象
     */
    User getUser(int parseInt);

    /**
     * @param bigsort_name 大分类名
     * @return 请求的集合
     */
    Collection<Task> getTaskFromBigSortName(String bigsort_name);

    /**
     * @return 大分类名集合
     */
    List<String> getBigSortName(int parent_sort_id);

    /**
     * @return 小分类名集合
     */
    List<String> getSmallSortName(int parent_sort_id);

    /**
     * @param task 存储信息的请求
     * @return 请求集合
     */
    List<Task> getTaskFromSearchName(Task task);

    /**
     * @param taskId 请求id
     * @return 请求图片集合
     */
    List<TaskPictures> getAllTaskPictures(String taskId);

    /**
     * @param userId 用户id
     * @param task_id 发布id
     * @param commentTime 评论时间
     * @param commenttext 评论内容
     */
    void setComment(long userId, String task_id, Timestamp commentTime, String commenttext);

    void updateComment(long commentId);

    void addTaskLikeCount(String task_id);

    void addCommentFromid(Comments comments);

}
