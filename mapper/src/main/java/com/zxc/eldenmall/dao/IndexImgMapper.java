package com.zxc.eldenmall.dao;

import com.zxc.eldenmall.entity.IndexImg;
import com.zxc.eldenmall.general.GeneralDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wahaha
 * 查询首页轮播图的信息
 */

@Repository
public interface IndexImgMapper extends GeneralDAO<IndexImg> {

    /**
     * 查询status = 1（上线），并且按照seq字段进行排序
     */
    public List<IndexImg> listIndexImgs();
}