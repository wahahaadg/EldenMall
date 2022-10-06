package com.zxc.eldenmall.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxc.eldenmall.dao.IndexImgMapper;
import com.zxc.eldenmall.entity.IndexImg;
import com.zxc.eldenmall.service.IndexImgService;
import com.zxc.eldenmall.vo.ResStatus;
import com.zxc.eldenmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wahaha
 */
@Service
public class IndexImgServiceImpl implements IndexImgService {

    @Autowired
    private IndexImgMapper indexImgMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ResultVO listIndexImgs() {
        List<IndexImg> indexImgs = null;
        try {
            //从redis中取id对应的数据
            String imgStr = redisTemplate.boundValueOps("indexImgs").get();
            //如果redis中存在数据，则取出json数据进行格式转换
            if(imgStr != null){
                JavaType type = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, IndexImg.class);
                indexImgs = objectMapper.readValue(imgStr, type);
            } else {
                //如果不存在则去数据库中查询
                indexImgs = indexImgMapper.listIndexImgs();
                redisTemplate.boundValueOps("indexImgs").set(objectMapper.writeValueAsString(indexImgs));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(indexImgs != null){
            return new ResultVO(ResStatus.OK, "success",indexImgs);
        } else {
            return new ResultVO(ResStatus.NO, "寄", null);
        }
    }
}
