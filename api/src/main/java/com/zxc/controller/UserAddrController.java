package com.zxc.controller;

import com.zxc.eldenmall.service.UserAddrService;
import com.zxc.eldenmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wahaha
 */
@RestController
@CrossOrigin
@Api(value = "提供收货地址相关接口",tags = "收货地址管理")
@RequestMapping("/useraddr")
public class UserAddrController {

    @Autowired
    private UserAddrService userAddrService;

    @GetMapping("/list")
    @ApiImplicitParam(dataTypeClass = Integer.class,dataType = "int",name = "userId", value = "用户ID",required = true)
    public ResultVO listAddr(Integer userId){
        return userAddrService.listAddrsByUid(userId);
    }

}
