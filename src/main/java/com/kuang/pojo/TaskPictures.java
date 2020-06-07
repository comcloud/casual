package com.kuang.pojo;

import lombok.*;

/**
 * @author HP
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class TaskPictures {
    private String taskId;
    private byte[] taskPicture;
}
