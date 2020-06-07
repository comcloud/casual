package com.kuang.service;

import com.kuang.pojo.Sorts;
import com.kuang.pojo.Task;
import com.kuang.pojo.TaskPictures;

import java.util.List;

/**
 * @author HP
 */
public interface TaskService {
    boolean update(TaskPictures taskPictures);

    boolean add(Task task);

    boolean add(TaskPictures taskPictures);

    List<Sorts> getParentSort();

    List<String> getListToSon(String parentSort);

    List<Sorts> getSons(String parentSort);

    List<Sorts> getSons(int parentSort);

    List<Task> getAllTasks();

    List<TaskPictures> getAllTaskPictures(String taskId);

    List<Task> getAllTasksByUserId(Integer integer);

}
