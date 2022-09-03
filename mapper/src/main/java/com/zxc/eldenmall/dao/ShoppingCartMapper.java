package com.zxc.eldenmall.dao;

import com.zxc.eldenmall.entity.ShoppingCart;
import com.zxc.eldenmall.entity.ShoppingCartVO;
import com.zxc.eldenmall.general.GeneralDAO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wahaha
 */
@Repository
public interface ShoppingCartMapper extends GeneralDAO<ShoppingCart> {

    /**
     * 购物车列表显示
     * @param userId 用户id
     * @return 列表
     */
    public List<ShoppingCartVO> selectShopcartByUserId(int userId);

    /**
     * 修改购物车数量接口
     * @param cartId 购物车id
     * @param cartNum 修改的数量
     * @return 相应修改的数量
     */
    public int updateCartNumByCartId(@Param("cartId") int cartId,
                                     @Param("cartNum") int cartNum);

    /**
     * 购物车列表显示接口
     * @param cids 购物车id集合
     * @return 列表
     */
    public List<ShoppingCartVO> selectShopCartByCids(List<Integer> cids);
}