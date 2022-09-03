package com.zxc.eldenmall.service.impl;

import com.zxc.eldenmall.dao.ShoppingCartMapper;
import com.zxc.eldenmall.entity.ShoppingCart;
import com.zxc.eldenmall.entity.ShoppingCartVO;
import com.zxc.eldenmall.service.ShoppingCartService;
import com.zxc.eldenmall.vo.ResStatus;
import com.zxc.eldenmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author wahaha
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public ResultVO addShoppingCart(ShoppingCart cart) {
        cart.setCartTime(sdf.format(new Date()));
        int insert = shoppingCartMapper.insert(cart);
        if(insert > 0){
            return new ResultVO(ResStatus.OK, "success", null);
        } else {
            return new ResultVO(ResStatus.NO, "寄", null);
        }
    }

    @Override
    public ResultVO listShoppingCartByUserId(int userId) {
        List<ShoppingCartVO> list = shoppingCartMapper.selectShopcartByUserId(userId);
        return new ResultVO(ResStatus.OK, "success" ,list);
    }

    @Override
    public ResultVO updateCartNum(int cartId, int cartNum) {
        int i = shoppingCartMapper.updateCartNumByCartId(cartId, cartNum);
        if(i > 0) {
            return new ResultVO(ResStatus.OK, "success", null);
        } else {
            return new ResultVO(ResStatus.NO, "寄", null);
        }
    }
}
