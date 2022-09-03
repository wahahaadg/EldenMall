package com.zxc.eldenmall.dao;

import com.zxc.eldenmall.entity.Category;
import com.zxc.eldenmall.entity.CategoryVO;
import com.zxc.eldenmall.general.GeneralDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wahaha
 */
@Repository
public interface CategoryMapper extends GeneralDAO<Category> {
    /**
     * 连接查询
     * @return 封装查询类别信息的类
     */
    public List<CategoryVO> selectAllCategories();

    /**
     * 子查询
     * @return 封装查询类别信息的类
     */
    public List<CategoryVO> selectAllCategories2();

    /**
     * 查询分类商品
     * @return 封装查询类别信息的类
     */
    public List<CategoryVO> selectFirstLevelCategories();
}