package com.zxc.eldenmall.service.impl;

import com.zxc.eldenmall.dao.IndexImgMapper;
import com.zxc.eldenmall.entity.IndexImg;
import com.zxc.eldenmall.service.IndexImgService;
import com.zxc.eldenmall.vo.ResStatus;
import com.zxc.eldenmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wahaha
 */
@Service
public class IndexImgServiceImpl implements IndexImgService {

    @Autowired
    private IndexImgMapper indexImgMapper;

    @Override
    public ResultVO listIndexImgs() {
        List<IndexImg> indexImgs = indexImgMapper.listIndexImgs();
        if(indexImgs.size() == 0){
            return new ResultVO(ResStatus.NO,"寄了",null);
        } else {
            return new ResultVO(ResStatus.OK,"success",indexImgs);
        }
    }
}
