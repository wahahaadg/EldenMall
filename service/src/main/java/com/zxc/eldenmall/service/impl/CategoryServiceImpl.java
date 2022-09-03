package com.zxc.eldenmall.service.impl;

import com.zxc.eldenmall.dao.CategoryMapper;
import com.zxc.eldenmall.entity.CategoryVO;
import com.zxc.eldenmall.service.CategoryService;
import com.zxc.eldenmall.vo.ResStatus;
import com.zxc.eldenmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wahaha
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public ResultVO listCategories() {
        List<CategoryVO> categoryVos = categoryMapper.selectAllCategories();

        return new ResultVO(ResStatus.OK, "success", categoryVos);
    }

    @Override
    public ResultVO listFirstLevelCategories() {
        List<CategoryVO> categoryVos = categoryMapper.selectFirstLevelCategories();

        return new ResultVO(ResStatus.OK, "success", categoryVos);
    }
}
