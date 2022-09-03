package com.zxc.eldenmall.service.impl;

import com.zxc.eldenmall.dao.ProductImgMapper;
import com.zxc.eldenmall.dao.ProductMapper;
import com.zxc.eldenmall.dao.ProductParamsMapper;
import com.zxc.eldenmall.dao.ProductSkuMapper;
import com.zxc.eldenmall.entity.*;
import com.zxc.eldenmall.service.ProductService;
import com.zxc.eldenmall.vo.ResStatus;
import com.zxc.eldenmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;

/**
 * @author wahaha
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductImgMapper productImgMapper;

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductParamsMapper productParamsMapper;

    @Override
    public ResultVO listRecommendProducts() {
        List<ProductVO> productVos = productMapper.selectRecommendProduct();

        return new ResultVO(ResStatus.OK,"success",productVos);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVO getProductBasicInfo(String productId) {
        //查询商品的基本信息
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId)
                //表示商品的状态是上架中
                .andEqualTo("productStatus",1);

        List<Product> products = productMapper.selectByExample(example);
        if(products.size() > 0){
            //如果查询到有商品，在进行商品图片和商品套餐的查询
            Example example1 = new Example(ProductImg.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andEqualTo("itemId",productId);
            List<ProductImg> productImgs = productImgMapper.selectByExample(example1);

            Example example2 = new Example(ProductSku.class);
            Example.Criteria criteria2 = example2.createCriteria();
            criteria2.andEqualTo("productId",productId)
                     .andEqualTo("status",1);
            List<ProductSku> productSkus = productSkuMapper.selectByExample(example2);

            HashMap<String , Object> basicInfo = new HashMap<>(8);
            basicInfo.put("product",products.get(0));
            basicInfo.put("productImgs",productImgs);
            basicInfo.put("productSkus",productSkus);

            return new ResultVO(ResStatus.OK,"success",basicInfo);

        } else {
            return new ResultVO(ResStatus.NO, "寄，什么也没有",null);
        }


    }

    @Override
    public ResultVO getProductParamsById(String productId) {
        Example example = new Example(ProductParams.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        List<ProductParams> productParams = productParamsMapper.selectByExample(example);
        if(productParams.size() > 0){
            return new ResultVO(ResStatus.OK,"success",productParams.get(0));
        } else {
            return new ResultVO(ResStatus.NO, "三无产品捏",null);
        }
    }
}
