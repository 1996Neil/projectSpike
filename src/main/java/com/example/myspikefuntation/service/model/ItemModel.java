package com.example.myspikefuntation.service.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wangzhe
 * @version 1.0
 * @ClassName ItemModel
 * @create 2021-08-05 16:02
 * @description 领域模型,在开发中往往是先设计除领域模型再设计数据库模型
 */
@Data
public class ItemModel {

    /**
     * 商品id
     **/
    private Integer id;
    /**
     * 商品名称
     **/
    private String title;
    /**
     * 商品价格
     **/
    private BigDecimal price;
    /**
     * 商品库存
     **/
    private Integer stock;
    /**
     * 商品描述
     **/
    private String description;
    /**
     * 商品销量
     **/
    private Integer sales;
    /**
     * 商品图片
     **/
    private String imgUrl;
}
