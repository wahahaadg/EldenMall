package com.zxc.controller;

import com.zxc.eldenmall.service.CategoryService;
import com.zxc.eldenmall.service.IndexImgService;
import com.zxc.eldenmall.service.ProductService;
import com.zxc.eldenmall.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wahaha
 */

@RestController
@RequestMapping("/index")
@Api(value = "提供首页的一系列功能", tags = "首页管理")
@CrossOrigin
public class IndexController {

    @Autowired
    IndexImgService indexImgService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @GetMapping("/indeximg")
    @ApiOperation("首页轮播图接口")
    public ResultVO listIndexImgs(){
        return indexImgService.listIndexImgs();
    }

    @GetMapping("/category-list")
    @ApiOperation("商品分类显示接口")
    public ResultVO listCategory(){
        return categoryService.listCategories();
    }

    @GetMapping("/list-recommends")
    @ApiOperation("首页新品推荐接口")
    public ResultVO listRecommendProducts(){
        return productService.listRecommendProducts();
    }

    @GetMapping("/category-recommends")
    @ApiOperation("分类商品推荐接口")
    public ResultVO listRecommendProductsByCategory(){
        return categoryService.listFirstLevelCategories();
    }

    
}
