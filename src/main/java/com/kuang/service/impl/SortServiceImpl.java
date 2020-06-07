package com.kuang.service.impl;

import com.kuang.mapper.SortMapper;
import com.kuang.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author HP
 */
@Service
public class SortServiceImpl implements SortService {
    /** @noinspection SpringJavaInjectionPointsAutowiringInspection*/
    @Autowired
    private SortMapper sortMapper;
    @Override
    public String getSortNameBySortId(int parentSort) {
        return sortMapper.selectSortNameBySortId(parentSort);
    }
}
