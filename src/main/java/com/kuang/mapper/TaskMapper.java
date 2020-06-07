package com.kuang.mapper;

import com.kuang.pojo.Sorts;
import com.kuang.pojo.Task;
import com.kuang.pojo.TaskPictures;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author HP
 */
@Mapper
public interface TaskMapper {
    boolean insertTask(@Param("task") Task task);

    boolean insertTaskPictures(@Param("taskPictures") TaskPictures taskPictures);

    List<Sorts> selectListToParent();

    List<String> selectListToSon(@Param("parentSort") String parentSort);

    List<Sorts> selectList(@Param("parentSort") String parentSort);

    List<Sorts> selectListById(@Param("parentSort") int parentSort);

    List<Task> selectAllTasks();

    List<TaskPictures> selectAllTaskPictures(String taskId);

    List<Task> selectAllTaskByUserId(Integer integer);

}
