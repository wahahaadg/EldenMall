package com.zxc.eldenmall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 新增 productName、productImg
 * @author wahaha
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartVO {
    private Integer cartId;
    private String productId;
    private String skuId;
    private String userId;
    private String cartNum;
    private String cartTime;
    private BigDecimal productPrice;
    private String skuProps;

    private String productName;
    private String productImg;
    private Double originalPrice;
    private Double sellPrice;
    private String skuName;
    /**
     * 库存
     */
    //private Integer skuStock;
}

