package com.zxc;

import com.zxc.eldenmall.dao.*;
import com.zxc.eldenmall.entity.*;
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

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Test
    void contextLoads() {
        List<Integer> cids = new ArrayList<>();
        cids.add(8);
        cids.add(10);
        List<ShoppingCartVO> list = shoppingCartMapper.selectShopCartByCids(cids);
        System.out.println(list);
    }

}
