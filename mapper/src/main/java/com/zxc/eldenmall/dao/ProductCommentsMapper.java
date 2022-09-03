package com.zxc.eldenmall.dao;

import com.zxc.eldenmall.entity.ProductComments;
import com.zxc.eldenmall.entity.ProductCommentsVO;
import com.zxc.eldenmall.general.GeneralDAO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wahaha
 */
@Repository
public interface ProductCommentsMapper extends GeneralDAO<ProductComments> {

    /**
     * 按照id查询评论
     * @param productId 传入的商品id
     * @param start 起始索引
     * @param limit 每页查询的条数
     * @return 返回查询的的评论实体类列表
     */
    public List<ProductCommentsVO> selectCommentsByProductId(@Param("productId") String productId ,
                                                             @Param("start")int start ,
                                                             @Param("limit")int limit);
}