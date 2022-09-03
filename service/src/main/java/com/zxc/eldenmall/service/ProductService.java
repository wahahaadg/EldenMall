package com.zxc.eldenmall.service;

import com.zxc.eldenmall.vo.ResultVO;

/**
 * @author wahaha
 */
public interface ProductService {

    /**
     * 商品推荐接口
     * @return 返回resultVo对象，包括状态，提示符，查询到的内容
     */
    public ResultVO listRecommendProducts();

    /**
     * 获取商品的基本信息
     * @param productId 传入的商品id
     * @return 返回resultVo对象，包括状态，提示符，查询到的内容
     */
    public ResultVO getProductBasicInfo(String productId);


    /**
     * 商品详情接口
     * @param productId 传入的商品id
     * @return 返回resultVo对象，包括状态，提示符，查询到的内容
     */
    public ResultVO getProductParamsById(String productId);
}
