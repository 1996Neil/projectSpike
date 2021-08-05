package com.example.myspikefuntation.service.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wangzhe
 * @version 1.0
 * @ClassName OrderModel
 * @create 2021-08-05 23:33
 * @description
 */
@Data
public class OrderModel {
    /**
     * 交易流水号
     **/
    private String id;
    /**
     * 用户id
     **/
    private Integer userId;
    /**
     * 商品id
     **/
    private Integer itemId;
    /**
     * 商品单价
     **/
    private BigDecimal itemPrice;
    /**
     * 购买数量
     **/
    private Integer amount;
    /**
     * 订单金额
     **/
    private BigDecimal orderPrice;


}
