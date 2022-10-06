package com.zxc.eldenmall.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxc.eldenmall.dao.ProductImgMapper;
import com.zxc.eldenmall.dao.ProductMapper;
import com.zxc.eldenmall.dao.ProductParamsMapper;
import com.zxc.eldenmall.dao.ProductSkuMapper;
import com.zxc.eldenmall.entity.*;
import com.zxc.eldenmall.service.ProductService;
import com.zxc.eldenmall.utils.PageHelper;
import com.zxc.eldenmall.vo.ResStatus;
import com.zxc.eldenmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
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

    @Autowired
    private StringRedisTemplate redisTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();



    @Override
    public ResultVO listRecommendProducts() {
        List<ProductVO> productVos = productMapper.selectRecommendProduct();

        return new ResultVO(ResStatus.OK,"success",productVos);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public ResultVO getProductBasicInfo(String productId) {
        try {
            //首先根据id查询redis
            String productsInfo = (String) redisTemplate.boundHashOps("products").get(productId);
            //查询到就去除信息并进行格式转换
            if(productsInfo != null){
                Product product = objectMapper.readValue(productsInfo, Product.class);
                //从redis中查询商品图片
                String imgStr = (String) redisTemplate.boundHashOps("productImgs").get(productId);
                JavaType type1 = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ProductImg.class);
                List<ProductImg> productImgs = objectMapper.readValue(imgStr,type1);
                //从redis中查询商品套餐
                String skuStr = (String) redisTemplate.boundHashOps("productSkus").get(productId);
                JavaType type2 = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, ProductSku.class);
                List<ProductSku> productSkus = objectMapper.readValue(skuStr,type2);
                //封装三种信息到map中
                HashMap<String,Object> basicInfo = new HashMap<>(16);
                basicInfo.put("product",product);
                basicInfo.put("productImgs",productImgs);
                basicInfo.put("productSkus",productSkus);
                return new ResultVO(ResStatus.OK,"success",basicInfo);
            } else {
                //未查询到则进行数据库的查询并添加到redis中
                //查询商品的基本信息
                Example example = new Example(Product.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("productId",productId)
                        //表示商品的状态是上架中
                        .andEqualTo("productStatus",1);
                List<Product> products = productMapper.selectByExample(example);
                if(products.size() > 0){
                    Product product = products.get(0);
                    String jsonStr = objectMapper.writeValueAsString(product);
                    redisTemplate.boundHashOps("products").put(productId,jsonStr);
                    //查询商品图片
                    Example example1 = new Example(ProductImg.class);
                    Example.Criteria criteria1 = example1.createCriteria();
                    criteria1.andEqualTo("itemId",productId);
                    List<ProductImg> productImgs = productImgMapper.selectByExample(example1);
                    redisTemplate.boundHashOps("productImgs").put(productId,objectMapper.writeValueAsString(productImgs));
                    //查询商品套餐
                    Example example2 = new Example(ProductSku.class);
                    Example.Criteria criteria2 = example2.createCriteria();
                    criteria2.andEqualTo("productId",productId)
                            .andEqualTo("status",1);
                    List<ProductSku> productSkus = productSkuMapper.selectByExample(example2);
                    redisTemplate.boundHashOps("productSkus").put(productId,objectMapper.writeValueAsString(productSkus));
                    //封装三种信息到map中
                    HashMap<String,Object> basicInfo = new HashMap<>(16);
                    basicInfo.put("product",products.get(0));
                    basicInfo.put("productImgs",productImgs);
                    basicInfo.put("productSkus",productSkus);

                    return new ResultVO(ResStatus.OK,"success",basicInfo);
                } else {
                    return new ResultVO(ResStatus.NO, "寄，什么也没有",null);
                }
            }
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }

        return null;

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

    @Override
    public ResultVO searchProductByKeyword(String kw, int pageNum, int limit) {
        //1.查询搜索结果
        kw = "%"+kw+"%";
        int start = (pageNum-1)*limit;
        List<ProductVO> productVOs = productMapper.selectProductByKeyword(kw, start, limit);

        //2.查询总记录数
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andLike("productName",kw);
        int count = productMapper.selectCountByExample(example);

        //3.计算总页数
        int pageCount = count%limit==0? count/limit:count/limit+1;

        //4.封装，返回数据
        PageHelper<ProductVO> pageHelper = new PageHelper<>(count, pageCount, productVOs);
        return new ResultVO(ResStatus.OK, "SUCCESS", pageHelper);
    }
}
