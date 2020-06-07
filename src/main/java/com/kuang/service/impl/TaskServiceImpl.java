package com.kuang.service.impl;

import com.kuang.mapper.TaskMapper;
import com.kuang.pojo.Sorts;
import com.kuang.pojo.Task;
import com.kuang.pojo.TaskPictures;
import com.kuang.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author HP
 */
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskMapper taskMapper;
    @Override
    public boolean update(TaskPictures taskPictures) {
        return false;
    }

    @Override
    public boolean add(Task task) {
        return taskMapper.insertTask(task);
    }

    @Override
    public boolean add(TaskPictures taskPictures) {
        return taskMapper.insertTaskPictures(taskPictures);
    }

    @Override
    public List<Sorts> getParentSort() {
        return taskMapper.selectListToParent();
    }

    @Override
    public List<String> getListToSon(String parentSort) {
        return taskMapper.selectListToSon(parentSort);
    }

    @Override
    public List<Sorts> getSons(String parentSort) {
        return taskMapper.selectList(parentSort);
    }

    @Override
    public List<Sorts> getSons(int parentSort) {
        return taskMapper.selectListById(parentSort);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskMapper.selectAllTasks();
    }

    @Override
    public List<TaskPictures> getAllTaskPictures(String taskId) {
        return taskMapper.selectAllTaskPictures(taskId);
    }

    @Override
    public List<Task> getAllTasksByUserId(Integer integer) {
        return taskMapper.selectAllTaskByUserId(integer);
    }
}
