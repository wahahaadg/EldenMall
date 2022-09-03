package com.zxc.eldenmall.service;

import com.zxc.eldenmall.vo.ResultVO;

/**
 * @author wahaha
 */
public interface UserService {
    /**
     * 注册接口
     * @param name 用户名
     * @param pwd 密码
     * @return 返回值就是一个有状态码，提示信息，数据的对象
     */
    public ResultVO userRegister(String name , String pwd);

    /**
     * 登录接口
     * @param name 用户名
     * @param pwd 密码
     * @return 返回值就是一个有状态码，提示信息，数据的对象
     */
    public ResultVO checkLogin(String name, String pwd);
}
