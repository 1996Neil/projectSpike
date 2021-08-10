package com.example.myspikefuntation.service.impl;

import com.example.myspikefuntation.error.BusinessException;
import com.example.myspikefuntation.error.EmBusinessError;
import com.example.myspikefuntation.mbg.dao.dataObject.OrderDO;
import com.example.myspikefuntation.mbg.dao.dataObject.SequenceDO;
import com.example.myspikefuntation.mbg.mapper.OrderDOMapper;
import com.example.myspikefuntation.mbg.mapper.SequenceDOMapper;
import com.example.myspikefuntation.service.ItemService;
import com.example.myspikefuntation.service.OrderService;
import com.example.myspikefuntation.service.UserService;
import com.example.myspikefuntation.service.model.ItemModel;
import com.example.myspikefuntation.service.model.OrderModel;
import com.example.myspikefuntation.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount,Integer promoId) throws BusinessException {
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
        if (promoId!=null) {
            //(1)校验对应活动是否存在这个商品
            if (promoId.intValue() != itemModel.getPromoModel().getId()) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "活动信息不正确");
                //2校验活动是否正在进行中
            } else if (itemModel.getPromoModel().getStatus().intValue()!=2){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "活动还未开始");
            }
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
        if (promoId!=null) {
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else {
            orderModel.setItemPrice(itemModel.getPrice());
        }
        //加入活动id
        orderModel.setPromoId(promoId);
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));
        //生成交易流水号,订单号
        orderModel.setId(generateOrderNo());
        OrderDO orderDO = this.covertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);
        //加上商品的销量
        itemService.increaseSales(itemId,amount);
        //返回前端
        return orderModel;
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public String generateOrderNo(){
        //订单号有16位
        StringBuilder stringBuilder = new StringBuilder();
        //前八位为时间信息,年月日
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        stringBuilder.append(nowDate);
        //中间六位为自增序列
        int sequence=0;
        //查询当前sequence对象
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        //把sequence的当前值提取出来,之后再把值和步数相加在数据库中更新
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue()+sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
        //当前值转化为字符串计算长度,不足位补正0
        String sequenceStr = String.valueOf(sequence);
        for(int i = 0; i < 6-sequenceStr.length(); i++) {
            stringBuilder.append(0);
        }
        //000001
        stringBuilder.append(sequenceStr);
        //最后两位为分库分表位
        stringBuilder.append("00");
        return stringBuilder.toString();
    }

    private OrderDO covertFromOrderModel(OrderModel orderModel){
        if (orderModel==null) {
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel,orderDO);
        orderDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderDO;
    }
}
