package com.zxc.eldenmall.service;

import com.zxc.eldenmall.vo.ResultVO;
import org.springframework.stereotype.Service;

/**
 * @author wahaha
 */
public interface CategoryService {
    /**
     * 查询商品列表
     * @return 查询的状态和信息
     */
    public ResultVO listCategories();

    /**
     * 查询定义分类商品
     * @return 查询的状态和信息
     */
    public ResultVO listFirstLevelCategories();
}
