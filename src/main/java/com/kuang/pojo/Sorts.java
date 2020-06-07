package com.kuang.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * (Sorts)实体类
 *
 * @author makejava
 * @since 2020-05-26 21:31:29
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Sorts implements Serializable {
    private static final long serialVersionUID = 112984107175374809L;
    /**
    * 分类ID
    */
    private Long sortId;
    /**
    * 分类名称
    */
    private String sortName;
    /**
    * 分类描述
    */
    private String sortDescription;
    /**
    * 父分类ID
    */
    private Long parentSortId;

    private List<Sorts> list;
}