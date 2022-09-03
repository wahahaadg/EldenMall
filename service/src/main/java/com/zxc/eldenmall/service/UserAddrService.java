package com.zxc.eldenmall.service;

import com.zxc.eldenmall.vo.ResultVO;

/**
 * @author wahaha
 */
public interface UserAddrService {

    /**
     * 用户地址查询接口
     * @param userId 用户id
     * @return 返回给前端的对象
     */
    public ResultVO listAddrsByUid(int userId);

}
