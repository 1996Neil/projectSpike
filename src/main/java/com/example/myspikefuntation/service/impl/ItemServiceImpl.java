package com.example.myspikefuntation.service.impl;

import com.example.myspikefuntation.error.BusinessException;
import com.example.myspikefuntation.error.EmBusinessError;
import com.example.myspikefuntation.mbg.dao.dataObject.ItemDO;
import com.example.myspikefuntation.mbg.dao.dataObject.ItemStockDO;
import com.example.myspikefuntation.mbg.mapper.ItemDOMapper;
import com.example.myspikefuntation.mbg.mapper.ItemStockDOMapper;
import com.example.myspikefuntation.service.ItemService;
import com.example.myspikefuntation.service.model.ItemModel;
import com.example.myspikefuntation.service.model.UserModel;
import com.example.myspikefuntation.validator.ValidatorImpl;
import com.example.myspikefuntation.validator.ValidatorResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangzhe
 * @version 1.0
 * @ClassName ItemServiceImpl
 * @create 2021-08-05 16:30
 * @description
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ItemDOMapper itemDOMapper;

    @Autowired
    private ItemStockDOMapper itemStockDOMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        //校验入参
        ValidatorResult result = validator.validate(itemModel);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        //转化itemModel->dataObject
        ItemDO itemDO = this.convertFromItemModel(itemModel);
        //写入数据库
        itemDOMapper.insertSelective(itemDO);
        itemModel.setId(itemDO.getId());
        ItemStockDO itemStockDO = this.convertStockFromItemModel(itemModel);
        itemStockDOMapper.insertSelective(itemStockDO);
        //返回创建完成的对象
        return this.getItemById(itemModel.getId());
    }
    private ItemDO convertFromItemModel(ItemModel itemModel){
        if (itemModel==null) {
            return null;
        }
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel,itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());
        return itemDO;
    }

    private ItemStockDO convertStockFromItemModel(ItemModel itemModel){
        if (itemModel==null) {
            return null;
        }
        ItemStockDO itemStockDO = new ItemStockDO();
        itemStockDO.setStock(itemModel.getStock());
        itemStockDO.setItemId(itemModel.getId());
        return itemStockDO;
    }

    @Override
    public List<ItemModel> listItem() {
        return null;
    }

    @Override
    public ItemModel getItemById(Integer itemId) {
        if (itemId==null) {
            return null;
        }

        return null;
    }
}
