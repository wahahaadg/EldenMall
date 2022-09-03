package com.zxc.controller;

import com.zxc.eldenmall.entity.ShoppingCart;
import com.zxc.eldenmall.service.ShoppingCartService;
import com.zxc.eldenmall.vo.ResStatus;
import com.zxc.eldenmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author wahaha
 */
@RestController
@RequestMapping("/shopcart")
@Api(value = "购物车相关的接口", tags = "购物车管理")
@CrossOrigin
public class ShoppingCartController {


    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    @ApiOperation("添加购物车接口")
    public ResultVO addShoppingCart(@RequestBody ShoppingCart cart){
        return shoppingCartService.addShoppingCart(cart);
    }

    @GetMapping("/list")
    @ApiImplicitParam(dataTypeClass = Integer.class, dataType = "int", name = "userId", value = "用户Id", required = true)
    @ApiOperation("用户id查询购物车列表接口")
    public ResultVO listShoppingCartByUserId(Integer userId){
        return  shoppingCartService.listShoppingCartByUserId(userId);
    }

    @PutMapping("/update/{cid}/{cnum}")
    @ApiOperation("购物车修改接口")
    public ResultVO updateCartNum( @PathVariable("cid") Integer cartId,
                                   @PathVariable("cnum") Integer cartNum){
        return shoppingCartService.updateCartNum(cartId, cartNum);
    }

    @GetMapping("/listbycids")
    @ApiImplicitParam(dataTypeClass = String.class, dataType = "String", name = "cids", value = "购物车id列表", required = true)
    @ApiOperation("购物车id查询购物车列表接口")
    public ResultVO listShoppingCartByCids(String cids , @RequestHeader("token") String token){
        return shoppingCartService.listShoppingCartByCids(cids);
    }
}
