package com.zxc.eldenmall.service.impl;

import com.zxc.eldenmall.dao.ProductCommentsMapper;
import com.zxc.eldenmall.entity.ProductComments;
import com.zxc.eldenmall.entity.ProductCommentsVO;
import com.zxc.eldenmall.service.ProductCommentsService;
import com.zxc.eldenmall.utils.PageHelper;
import com.zxc.eldenmall.vo.ResStatus;
import com.zxc.eldenmall.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;

/**
 * @author wahaha
 */
@Service
public class ProductCommentsServiceImpl implements ProductCommentsService {

    @Autowired
    private ProductCommentsMapper productCommentsMapper;

    @Override
    public ResultVO listCommentsByProductId(String productId, int pageNum, int limit) {
        //1.根据id查询总记录数
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId", productId);
        int count = productCommentsMapper.selectCountByExample(example);

        //2.根据总记录数和每页条数计算出页码
        int pageCount = count%limit == 0 ? count/limit : count/limit + 1 ;

        //3.初始的下标
        int start = (pageNum - 1) * limit;

        List<ProductCommentsVO> list = productCommentsMapper.selectCommentsByProductId(productId, start, limit);

        return new ResultVO(ResStatus.OK,"success",new PageHelper<ProductCommentsVO>(count , pageCount, list));
    }

    @Override
    public ResultVO getCommentCountByProductId(String productId) {

        //1.查询当前商品评价的总数
        Example example = new Example(ProductComments.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId",productId);
        int total = productCommentsMapper.selectCountByExample(example);

        //2.查询好评评价数
        criteria.andEqualTo("commType",1);
        int goodTotal = productCommentsMapper.selectCountByExample(example);

        //3.查询中评评价数
        Example example1 = new Example(ProductComments.class);
        Example.Criteria criteria1 =  example1.createCriteria();
        criteria1.andEqualTo("productId",productId);
        criteria1.andEqualTo("commType",0);
        int midTotal = productCommentsMapper.selectCountByExample(example1);

        //4.查询差评评价数
        Example example2 = new Example(ProductComments.class);
        Example.Criteria criteria2 =  example2.createCriteria();
        criteria2.andEqualTo("productId",productId);
        criteria2.andEqualTo("commType",-1);
        int badTotal = productCommentsMapper.selectCountByExample(example2);

        //5.计算好评率
        double percent = (Double.parseDouble(goodTotal+"") / Double.parseDouble(total+"") )*100;
        String percentValue = (percent+"").substring(0,(percent+"").lastIndexOf(".")+3);

        HashMap<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("goodTotal",goodTotal);
        map.put("midTotal",midTotal);
        map.put("badTotal",badTotal);
        map.put("percent",percentValue);

        return new ResultVO(ResStatus.OK, "success", map);
    }
}
