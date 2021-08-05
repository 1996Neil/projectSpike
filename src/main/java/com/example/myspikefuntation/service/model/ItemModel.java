package com.example.myspikefuntation.service.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotBlank(message = "商品名称不能为空")
    private String title;
    /**
     * 商品价格
     **/
    @NotNull(message = "商品价格不能为空")
    @Min(value = 0,message = "商品价格不能为0")
    private BigDecimal price;
    /**
     * 商品库存
     **/
    @NotNull(message = "商品库存不能为空")
    private Integer stock;
    /**
     * 商品描述
     **/
    @NotBlank(message = "商品描述不能为空")
    private String description;
    /**
     * 商品销量
     **/
    private Integer sales;
    /**
     * 商品图片
     **/
    @NotBlank(message = "商品图片不能为空")
    private String imgUrl;
}
