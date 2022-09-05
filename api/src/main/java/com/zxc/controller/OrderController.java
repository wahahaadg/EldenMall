package com.zxc.controller;

import com.github.wxpay.sdk.WXPay;
import com.zxc.eldenmall.service.job.MyPayConfig;
import com.zxc.eldenmall.entity.Orders;
import com.zxc.eldenmall.service.OrderService;
import com.zxc.eldenmall.vo.ResStatus;
import com.zxc.eldenmall.vo.ResultVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wahaha
 */
@RestController
@RequestMapping("/order")
@Api(value = "提供关于订单的接口", tags = "订单管理")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResultVO addOrder(String cids, @RequestBody Orders orders){
        Map<String, String> map = null;
        try {
            map = orderService.addOrder(cids, orders);
            if(map != null){
                HashMap<String, String> data = new HashMap<>(16);
                data.put("out_trade_no",map.get("orderId"));
                data.put("body",map.get("productNames"));
                data.put("fee_type","CNY");
                data.put("total_fee","1");
                data.put("trade_type","NATIVE");
                data.put("notify_url","http://elden.free.idcfengye.com/pay/callback");

                WXPay wxPay = new WXPay(new MyPayConfig());
                Map<String, String> resp = wxPay.unifiedOrder(data);
                System.out.println(resp);
                map.put("payUrl",resp.get("code_url"));
            } else {
                return new ResultVO(ResStatus.NO, "订单提交失败", null);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return new ResultVO(ResStatus.NO, "订单提交失败", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultVO(ResStatus.OK, "success", map);
    }

    @GetMapping("/status/{oid}")
    public ResultVO getOrderStatus(@PathVariable("oid") String orderId){
        return orderService.getOrderStatusById(orderId);
    }
}
