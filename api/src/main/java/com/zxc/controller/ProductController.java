package com.zxc.controller;

import com.zxc.eldenmall.service.ProductCommentsService;
import com.zxc.eldenmall.service.ProductService;
import com.zxc.eldenmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wahaha
 */
@RestController
@RequestMapping("/product")
@Api(value = "提供商品信息相关的接口", tags = "商品管理")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCommentsService productCommentsService;

    @ApiOperation("商品基本信息查询接口")
    @GetMapping("/detail-info/{pid}")
    public ResultVO getProductBasicInfo(@PathVariable("pid") String pid){
        return productService.getProductBasicInfo(pid);
    }

    @ApiOperation("商品基本参数查询接口")
    @GetMapping("/detail-params/{pid}")
    public ResultVO getProductParams(@PathVariable("pid") String pid){
        return productService.getProductParamsById(pid);
    }

    @ApiOperation("商品评论查询接口")
    @GetMapping("/detail-comments/{pid}")
    @ApiImplicitParams({
            @ApiImplicitParam(dataTypeClass = String.class, dataType = "string", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(dataTypeClass = String.class, dataType = "string", name = "limit", value = "每页显示条数", required = true)
    })
    public ResultVO getProductComments(@PathVariable("pid") String pid, int pageNum, int limit){
        return productCommentsService.listCommentsByProductId(pid, pageNum, limit);
    }

    @ApiOperation("商品评论总数及分布查询接口")
    @GetMapping("/detail-commentscount/{pid}")
    public ResultVO getProductCommentCountByProductId(@PathVariable("pid") String pid){
        return productCommentsService.getCommentCountByProductId(pid);
    }

}
