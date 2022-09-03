package com.zxc.eldenmall.dao;

import com.zxc.eldenmall.entity.Product;
import com.zxc.eldenmall.entity.ProductVO;
import com.zxc.eldenmall.general.GeneralDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wahaha
 */
@Repository
public interface ProductMapper extends GeneralDAO<Product> {
    /**
     * 推荐商品的接口
     * @return 查询到的商品列表并包含图片
     */
    public List<ProductVO> selectRecommendProduct();

    /**
     * 查询指定一级类别下销量最高的六个商品
     * @param cid 传入的类别编号
     * @return 查询到的商品列表并包含图片
     */

    public List<ProductVO> selectTop6ByCategory(int cid);
}