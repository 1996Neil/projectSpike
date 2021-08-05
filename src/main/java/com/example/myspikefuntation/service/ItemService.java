package com.example.myspikefuntation.service;

import com.example.myspikefuntation.error.BusinessException;
import com.example.myspikefuntation.service.model.ItemModel;
import org.hibernate.validator.constraints.time.DurationMax;

import java.util.List;

/**
 * @author wangzhe
 * @version 1.0
 * @ClassName itemService
 * @create 2021-08-05 16:26
 * @description
 */
public interface ItemService {

    /**
     * 创建商品
     * @param itemModel
     * @return
     * @throws BusinessException
     */
    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    /**
     * 商品列表浏览
     * @Date 16:29 2021/8/5
     * @param
     * @return  java.util.List<com.example.myspikefuntation.service.model.ItemModel>
     **/
    List<ItemModel> listItem();

    /**
     * 商品详情浏览
     * @Date 16:29 2021/8/5
     * @param itemId
     * @return  com.example.myspikefuntation.service.model.ItemModel
     **/
    ItemModel getItemById(Integer itemId);
    /**
     * 扣减商品库存
     * @Date 0:00 2021/8/6
     * @param itemId
     * @param amount 商品数量
     * @return  boolean
     * @throws BusinessException
     **/
    boolean decreaseStock(Integer itemId,Integer amount)throws BusinessException;
}
