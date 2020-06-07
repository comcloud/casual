package com.kuang.pojo;

import lombok.*;

import java.sql.Timestamp;

/**
 * @author HP
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Task {
    private String taskId;
    private int userId;
    private String taskTitle;
    private String taskDescription;
    private int taskViews;
    private Timestamp taskDate;
    private int taskLikeCount;
    private String taskAddress;
    private String parentSort;
    private String sonSort;

    public Task setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
        return this;
    }

    public Task setTaskAddress(String taskAddress) {
        this.taskAddress = taskAddress;
        return this;
    }

    public Task setParentSort(String parentSort) {
        this.parentSort = parentSort;
        return this;
    }

    public Task setSonSort(String sonSort) {
        this.sonSort = sonSort;
        return this;
    }

}
