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
     * @throws SQLException 可能会有数据库异常
     * @return 给前端的数据
     */
    public Map<String, String> addOrder(String cids, Orders order) throws SQLException;

    /**
     * 修改订单状态接口
     * @param orderId 传入的订单id
     * @param status 商品的状态
     * @return 判断的值
     */
    public int updateOrderStatus(String orderId, String status);

    /**
     * 根据订单id查询订单状态
     * @param orderId 传入的订单id
     * @return 返回给前端的对象
     */
    public ResultVO getOrderStatusById(String orderId);

    /**
     * 关闭订单接口
     * @param orderId 传入的订单id
     */
    public void closeOrder(String orderId);
}
