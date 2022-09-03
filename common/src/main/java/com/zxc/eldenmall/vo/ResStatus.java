package com.zxc.eldenmall.vo;

/**
 * @author wahaha
 */
public class ResStatus {

    public static final int OK=10000;
    public static final int NO=10001;

    /**
     * 认证成功
     */
    public static final int LOGIN_SUCCESS = 20000;

    /**
     * 用户未登录
     */
    public static final int LOGIN_FAIL_NOT = 20001;

    /**
     * 用户登录失效
     */
    public static final int LOGIN_FAIL_OVERDUE = 20002;

}

