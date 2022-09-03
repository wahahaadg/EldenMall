package com.zxc.eldenmall.service;

import com.zxc.eldenmall.vo.ResultVO;

/**
 * @author wahaha
 */
public interface ProductCommentsService {
    /**
     * 返回评论列表接口
     * @param productId 商品id
     * @param pageNum 页码
     * @param limit 每页条数
     * @return 返回评论列表
     */
    public ResultVO listCommentsByProductId(String productId , int pageNum , int limit);


    /**
     * 评价统计接口
     * @param productId 商品id
     * @return 返回resultVo
     */
    public ResultVO getCommentCountByProductId(String productId);
}
