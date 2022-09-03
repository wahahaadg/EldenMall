package com.zxc.eldenmall.service;

import com.zxc.eldenmall.vo.ResultVO;

/**
 * @author wahaha
 */
public interface IndexImgService {

    /**
     * 这是一个查询轮播图的接口
     * @return 返回值就是一个有状态码，提示信息，数据的对象
     */
    public ResultVO listIndexImgs();
}
