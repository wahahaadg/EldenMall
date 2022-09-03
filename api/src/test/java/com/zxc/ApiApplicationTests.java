package com.zxc;

import com.zxc.eldenmall.dao.CategoryMapper;
import com.zxc.eldenmall.dao.ProductCommentsMapper;
import com.zxc.eldenmall.dao.ProductImgMapper;
import com.zxc.eldenmall.dao.ProductMapper;
import com.zxc.eldenmall.entity.CategoryVO;
import com.zxc.eldenmall.entity.ProductCommentsVO;
import com.zxc.eldenmall.entity.ProductImg;
import com.zxc.eldenmall.entity.ProductVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;


@SpringBootTest( classes = ApiApplication.class)
class ApiApplicationTests {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductCommentsMapper productCommentsMapper;

    @Test
    void contextLoads() {
        List<ProductCommentsVO> productCommentsVOS = productCommentsMapper.selectCommentsByProductId("3" , 3 , 1);

        for(ProductCommentsVO p : productCommentsVOS){
            System.out.println(p);
        }

    }
}
