package com.zxc.eldenmall.service;

import com.zxc.eldenmall.entity.Orders;
import com.zxc.eldenmall.vo.ResultVO;

import java.sql.SQLException;
import java.util.Map;

/**
 * @author wahaha
 */
public interface OrderService {

    /**
     * 添加订单接口
     * @param cids 传入的购物车id
     * @param order 传入的订单对象,包含订单的基本内容
     * @return 给前端的数据
     */
    public Map<String, String> addOrder(String cids, Orders order) throws SQLException;
}
