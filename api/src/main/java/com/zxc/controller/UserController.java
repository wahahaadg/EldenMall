package com.zxc.controller;

import com.zxc.eldenmall.entity.Users;
import com.zxc.eldenmall.service.UserService;
import com.zxc.eldenmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户管理的接口
 * @author wahaha
 *
 * `@RestController`注解是`@Controller`和`@ResponseBody`的合集,
 * 表示这是个控制器 bean,并且是将函数的返回值直接填入 HTTP 响应体中,是 REST 风格的控制器。
 *
 * `@RequestMapping`是一个用来处理请求地址映射的注解
 */
@RestController
@RequestMapping("/user")
@Api(value = "提供用户的登录和注册接口", tags = "用户管理")
@CrossOrigin
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation("用户登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataTypeClass = String.class, dataType = "string", name = "username", value = "用户登录账号", required = true),
            @ApiImplicitParam(dataTypeClass = String.class, dataType = "string", name = "password", value = "用户登录密码", required = true)
    })
    @GetMapping("/login")
    public ResultVO login(@RequestParam("username") String name,
                          @RequestParam(value = "password") String pwd) {

        return userService.checkLogin(name, pwd);
    }

    @ApiOperation("用户注册接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataTypeClass = String.class, dataType = "string", name = "username", value = "用户注册账号", required = true),
            @ApiImplicitParam(dataTypeClass = String.class, dataType = "string", name = "password", value = "用户注册密码", required = true)
    })
    @PostMapping("/register")
    public ResultVO register(@RequestBody Users user) {
        return userService.userRegister(user.getUsername(), user.getPassword());
    }
}
