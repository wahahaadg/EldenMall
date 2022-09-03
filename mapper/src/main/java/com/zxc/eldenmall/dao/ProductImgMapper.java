package com.zxc.eldenmall.dao;

import com.zxc.eldenmall.entity.ProductImg;
import com.zxc.eldenmall.general.GeneralDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wahaha
 */
@Repository
public interface ProductImgMapper extends GeneralDAO<ProductImg> {
    /**
     * 查询商品图片接口
     * @param productId 传入的商品id
     * @return 返回查询到的商品图片列表
     */
    public List<ProductImg> selectProductImgByProductId(int productId);
}