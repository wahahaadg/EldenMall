package com.zxc.controller;

import com.zxc.eldenmall.entity.Orders;
import com.zxc.eldenmall.service.OrderService;
import com.zxc.eldenmall.vo.ResStatus;
import com.zxc.eldenmall.vo.ResultVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

/**
 * @author wahaha
 */
@RestController
@RequestMapping("/order")
@Api(value = "提供关于订单", tags = "订单管理")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResultVO addOrder(String cids, @RequestBody Orders orders){
        Map<String, String> map;
        try {
            map = orderService.addOrder(cids, orders);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResultVO(ResStatus.NO, "订单提交失败", null);
        }
        return new ResultVO(ResStatus.OK, "success", map);
    }
}
