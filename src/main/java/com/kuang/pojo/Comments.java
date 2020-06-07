package com.kuang.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @Author :
 * @Description :
 * @Date : 2020/5/31 11:03
 * @Version ：1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comments {
    private int commentId;
    private int userId;
    private String taskId;
    private Timestamp commentDate;
    private String commentContent;
    private long parentCommentId;

    public Comments setCommentId(int commentId) {
        this.commentId = commentId;
        return this;
    }

    public Comments setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public Comments setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public Comments setCommentDate(Timestamp commentDate) {
        this.commentDate = commentDate;
        return this;
    }

    public Comments setCommentContent(String commentContent) {
        this.commentContent = commentContent;
        return this;
    }

    public Comments setParentCommentId(long parentCommentId) {
        this.parentCommentId = parentCommentId;
        return this;
    }
}
