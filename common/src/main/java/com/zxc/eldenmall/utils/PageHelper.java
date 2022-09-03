package com.zxc.eldenmall.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wahaha
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageHelper<T> {

    /**
     * 总记录数
     */
    private int count;

    /**
     * 总页数
     */
    private int pageCount;

    /**
     * 分页的数据
     */
    private List<T> list;

}
