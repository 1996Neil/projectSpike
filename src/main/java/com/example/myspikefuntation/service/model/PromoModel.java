package com.example.myspikefuntation.service.model;

import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * @author wangzhe
 * @version 1.0
 * @ClassName PromoModel
 * @create 2021-08-06 13:24
 * @description
 */
@Data
public class PromoModel {

    private Integer id;
    /**
     * 秒杀名称
     **/
    private String promoName;
    /**
     * 秒杀的开始时间
     **/
    private DateTime startTime;
    /**
     * 秒杀的商品名称
     **/
    private Integer itemId;
    /**
     * 秒杀的活动价格
     **/
    private BigDecimal promoItemPrice;
}
