package com.zxc.eldenmall.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxc.eldenmall.dao.CategoryMapper;
import com.zxc.eldenmall.entity.CategoryVO;
import com.zxc.eldenmall.service.CategoryService;
import com.zxc.eldenmall.vo.ResStatus;
import com.zxc.eldenmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wahaha
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public ResultVO listCategories() {
        List<CategoryVO> categoryVos = null;
        try {
            //从redis中进行查询
            String categories = redisTemplate.boundValueOps("categories").get();
            //查到则直接转换格式输出
            if(categories !=null){
                JavaType type = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, CategoryVO.class);
                categoryVos = objectMapper.readValue(categories,type);
            } else {
                //没查到则到数据库中查找
                categoryVos = categoryMapper.selectAllCategories();
                String str = objectMapper.writeValueAsString(categoryVos);
                redisTemplate.boundValueOps("categories").set(str,1, TimeUnit.DAYS);
            }
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return new ResultVO(ResStatus.OK,"success",categoryVos);
    }

    @Override
    public ResultVO listFirstLevelCategories() {
        List<CategoryVO> categoryVos = categoryMapper.selectFirstLevelCategories();

        return new ResultVO(ResStatus.OK, "success", categoryVos);
    }
}
