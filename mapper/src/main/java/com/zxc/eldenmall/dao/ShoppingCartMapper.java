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

    public int updateCartNumByCartId(@Param("cartId") int cartId,
                                     @Param("cartNum") int cartNum);
}