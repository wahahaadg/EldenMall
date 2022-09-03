package com.zxc.eldenmall.service;

import com.zxc.eldenmall.entity.ShoppingCart;
import com.zxc.eldenmall.vo.ResultVO;

/**
 * @author wahaha
 */
public interface ShoppingCartService {

    /**
     * 添加购物车
     * @param cart 购物车
     * @return 给前端的对象
     */
    public ResultVO addShoppingCart(ShoppingCart cart);

    /**
     * 购物车列表显示查询接口
     * @param userId 传入的购物车用户id
     * @return 给前端的对象
     */
    public ResultVO listShoppingCartByUserId(int userId);

    /**
     * 购物车内容修改
     * @param cartId 传入的用户id
     * @param cartNum 传入的修改数量
     * @return 给前端的对象
     */
    public ResultVO updateCartNum(int cartId, int cartNum);
}
