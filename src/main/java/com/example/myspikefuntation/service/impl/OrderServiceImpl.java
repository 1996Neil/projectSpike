package com.example.myspikefuntation.service.impl;

import com.example.myspikefuntation.error.BusinessException;
import com.example.myspikefuntation.error.EmBusinessError;
import com.example.myspikefuntation.mbg.dao.dataObject.OrderDO;
import com.example.myspikefuntation.mbg.mapper.OrderDOMapper;
import com.example.myspikefuntation.service.ItemService;
import com.example.myspikefuntation.service.OrderService;
import com.example.myspikefuntation.service.UserService;
import com.example.myspikefuntation.service.model.ItemModel;
import com.example.myspikefuntation.service.model.OrderModel;
import com.example.myspikefuntation.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author wangzhe
 * @version 1.0
 * @ClassName OrderServiceImpl
 * @create 2021-08-05 23:48
 * @description
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderDOMapper orderDOMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException {
        //1.校验下单状态,下单的商品是否存在,用户是否合法,购买数量是否正确
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel==null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品不存在");
        }
        UserModel userModel = userService.getUserById(userId);
        if (userModel==null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户不存在");
        }
        if (amount<=0 || amount>99) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品数量异常");
        }
        //2.落单减库存
        boolean result = itemService.decreaseStock(itemId, amount);
        if (!result) {
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }
        //订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        orderModel.setItemPrice(itemModel.getPrice());
        orderModel.setOrderPrice(itemModel.getPrice().multiply(new BigDecimal(amount)));
        //生成交易流水号,订单号
        OrderDO orderDO = this.covertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);
        //返回前端
        return null;
    }
    private String generateOrderNo(){
        //订单号有16位
        //前八位为时间信息,年月日
        //中间六位为自增序列
        //最后两位为分库分表位
        return null;
    }

    private OrderDO covertFromOrderModel(OrderModel orderModel){
        if (orderModel==null) {
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel,orderDO);
        return orderDO;
    }
}
