package com.zxc.eldenmall.service.impl;

import com.zxc.eldenmall.dao.OrderItemMapper;
import com.zxc.eldenmall.dao.OrdersMapper;
import com.zxc.eldenmall.dao.ProductSkuMapper;
import com.zxc.eldenmall.dao.ShoppingCartMapper;
import com.zxc.eldenmall.entity.OrderItem;
import com.zxc.eldenmall.entity.Orders;
import com.zxc.eldenmall.entity.ProductSku;
import com.zxc.eldenmall.entity.ShoppingCartVO;
import com.zxc.eldenmall.service.OrderService;
import com.zxc.eldenmall.vo.ResStatus;
import com.zxc.eldenmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

/**
 * @author wahaha
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> addOrder(String cids, Orders order) throws SQLException {
        /*logger.info("add order begin...");*/
        Map<String,String> map = new HashMap<>(8);

        //1.校验库存：根据cids查询当前订单中关联的购物车记录详情（包括库存）
        String[] arr = cids.split(",");
        List<Integer> cidsList = new ArrayList<>();
        for (String s : arr) {
            cidsList.add(Integer.parseInt(s));
        }
        List<ShoppingCartVO> list = shoppingCartMapper.selectShopCartByCids(cidsList);

        boolean f = true;
        StringBuilder untitled = new StringBuilder();
        for (ShoppingCartVO sc: list) {
            if(Integer.parseInt(sc.getCartNum()) > sc.getSkuStock()){
                f = false;
            }
            //获取所有商品名称，以,分割拼接成字符串
            untitled.append(sc.getProductName()).append(",");
        }

        if(f){
            /*logger.info("product stock is OK...");*/

            //2.保存订单
            order.setUntitled(untitled.toString());
            order.setCreateTime(new Date());
            order.setStatus("1");
            //生成订单编号
            String orderId = UUID.randomUUID().toString().replace("-", "");
            order.setOrderId(orderId);
            int i = ordersMapper.insert(order);

            //3.生成商品快照
            for (ShoppingCartVO sc: list) {
                int cnum = Integer.parseInt(sc.getCartNum());
                String itemId = System.currentTimeMillis()+""+ (new Random().nextInt(89999)+10000);
                OrderItem orderItem = new OrderItem(itemId, orderId, sc.getProductId(),
                        sc.getProductName(), sc.getProductImg(),
                        sc.getSkuId(), sc.getSkuName(),
                        BigDecimal.valueOf(sc.getSellPrice()), cnum,
                        new BigDecimal(sc.getSellPrice() * cnum),
                        new Date(), new Date(), 0);
                //每次生成一个快照就进行保存
                orderItemMapper.insert(orderItem);
            }

            //4.扣减库存：根据套餐ID修改套餐库存量
            for (ShoppingCartVO sc: list) {
                String skuId = sc.getSkuId();
                //剩余的新的库存数量
                int newStock = sc.getSkuStock()- Integer.parseInt(sc.getCartNum());

                ProductSku productSku = new ProductSku();
                productSku.setSkuId(skuId);
                productSku.setStock(newStock);
                //选中的字段进行修改,为选中的保持原来的值
                productSkuMapper.updateByPrimaryKeySelective(productSku);
            }

            //5.删除购物车：当购物车中的记录购买成功之后，购物车中对应做删除操作
            for (int cid: cidsList) {
                shoppingCartMapper.deleteByPrimaryKey(cid);
            }
            /*logger.info("add order finished...");*/
            map.put("orderId",orderId);
            map.put("productNames", untitled.toString());
            return map;

        }else{
            //表示库存不足
            return null;

        }

    }

    @Override
    public int updateOrderStatus(String orderId, String status) {
        Orders orders = new Orders();
        orders.setOrderId(orderId);
        orders.setStatus(status);
        int i = ordersMapper.updateByPrimaryKeySelective(orders);
        return i;
    }

    @Override
    public ResultVO getOrderStatusById(String orderId) {
        Orders orders = ordersMapper.selectByPrimaryKey(orderId);
        return new ResultVO(ResStatus.OK, "success", orders.getStatus());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE,rollbackFor = Exception.class)
    public void closeOrder(String orderId) {
        synchronized (this) {
            //  1.修改当前订单：status=6 已关闭  close_type=1 超时未支付
            Orders cancleOrder = new Orders();
            cancleOrder.setOrderId(orderId);
            //已关闭
            cancleOrder.setStatus("6");
            //关闭类型：超时未支付
            cancleOrder.setCloseType(1);
            ordersMapper.updateByPrimaryKeySelective(cancleOrder);

            //  2.还原库存：先根据当前订单编号查询商品快照（skuid  buy_count）--->修改product_sku
            Example example1 = new Example(OrderItem.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andEqualTo("orderId", orderId);
            List<OrderItem> orderItems = orderItemMapper.selectByExample(example1);
            //还原库存
            for (OrderItem orderItem : orderItems) {
                //修改
                ProductSku productSku = productSkuMapper.selectByPrimaryKey(orderItem.getSkuId());
                productSku.setStock(productSku.getStock() + orderItem.getBuyCounts());
                productSkuMapper.updateByPrimaryKey(productSku);
            }
        }
    }
}
