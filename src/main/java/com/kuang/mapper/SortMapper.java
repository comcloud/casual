package com.kuang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author HP
 */
@Mapper
public interface SortMapper {
    String selectSortNameBySortId(@Param("sortId") int parentSort);
}
