package com.kuang.service.impl;

import com.kuang.mapper.IndexMapper;
import com.kuang.pojo.*;
import com.kuang.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private IndexMapper indexMapper;

    @Override
    public User login(String user_name, String user_password) {
        return indexMapper.getlogin(user_name,user_password);
    }

    @Override
    public void  register(String user_nickname, String user_name, String user_password) {
        indexMapper.getregister(user_nickname,user_name,user_password);
    }

    @Override
    public Collection<Sorts> getAll(int parent_id) {
        return indexMapper.getall(parent_id);
    }

    @Override
    public List<Task> getTaskFromName(String sort_name) {
        return indexMapper.gettaskfromname(sort_name);
    }

    @Override
    public Collection<Sorts> getAllSmallSorts(int parent_sort_id) {
        return indexMapper.getallsmallsorts(parent_sort_id);
    }

    @Override
    public List<Task> getTaskAll() {
        return indexMapper.gettask();
    }

    @Override
    public Task gettaskfromid(String task_id) {
        return indexMapper.gettaskfromid(task_id);
    }

    @Override
    public Collection<Comments> getComments(String task_id) {
        return indexMapper.getcomments(task_id);
    }

    @Override
    public List<TaskPictures> getPicture() {
        return indexMapper.getpic();
    }

    @Override
    public User get(String uuid) {
        return indexMapper.selectByUuid(uuid);
    }

    @Override
    public boolean add(User user) {
        return indexMapper.insertOne(user)>0;
    }

    @Override
    public User getUser(int parseInt) {
        return indexMapper.selectUserByUserId(parseInt);
    }

    @Override
    public Collection<Task> getTaskFromBigSortName(String bigsort_name) {
        return indexMapper.gettaskfrombigsortname(bigsort_name);
    }

    @Override
    public List<String> getBigSortName(int parent_sort_id) {
        return indexMapper.getbigsortname(parent_sort_id);
    }

    @Override
    public List<String> getSmallSortName(int parent_sort_id) {
        return indexMapper.getsmallsortname(parent_sort_id);
    }

    @Override
    public List<Task> getTaskFromSearchName(Task task) {
        return indexMapper.gettaskfromsearchname(task);
    }

    @Override
    public List<TaskPictures> getAllTaskPictures(String taskId) {
        return indexMapper.getalltaskPic(taskId);
    }

    @Override
    public void setComment(long userId, String task_id, Timestamp commentTime, String commenttext) {
        indexMapper.setcomment(userId,task_id,commentTime,commenttext);
    }

    @Override
    public void updateComment(long commentId) {
        indexMapper.updatecomment(commentId);
    }

    @Override
    public void addTaskLikeCount(String task_id) {
        indexMapper.addtasklikecount(task_id);
    }

    @Override
    public void addCommentFromid(Comments comments) {
        indexMapper.addcommentformid(comments);
    }


}
