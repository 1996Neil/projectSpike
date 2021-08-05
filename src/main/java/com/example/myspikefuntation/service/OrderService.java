package com.example.myspikefuntation.service;

import com.example.myspikefuntation.error.BusinessException;
import com.example.myspikefuntation.service.model.OrderModel;

/**
 * @author wangzhe
 * @version 1.0
 * @ClassName OrderService
 * @create 2021-08-05 23:46
 * @description
 */
public interface OrderService {

    /**
     * 创建订单
     * @Date 23:48 2021/8/5
     * @param userId 用户id
     * @param itemId 哪一个商品
     * @param amount 商品数量
     * @return  com.example.myspikefuntation.service.model.OrderModel
     **/
    OrderModel createOrder(Integer userId,Integer itemId,Integer amount) throws BusinessException;
}
