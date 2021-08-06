package com.example.myspikefuntation.service.impl;

import com.example.myspikefuntation.mbg.dao.dataObject.PromoDO;
import com.example.myspikefuntation.mbg.mapper.PromoDOMapper;
import com.example.myspikefuntation.service.PromoService;
import com.example.myspikefuntation.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author wangzhe
 * @version 1.0
 * @ClassName PromoServiceImpl
 * @create 2021-08-06 13:38
 * @description
 */
@Service
public class PromoServiceImpl implements PromoService {
    @Autowired
    private PromoDOMapper promoDOMapper;
    @Override
    public PromoModel getPromoById(Integer itemId) {
        //获取对应商品的秒杀活动信息
        PromoDO promoDO = promoDOMapper.selectByByItemId(itemId);
        //dataObject->model
        PromoModel promoModel = covertFromDataObject(promoDO);
        if (promoModel==null) {
            return null;
        }
        //判断当前时间是否秒杀活动即将开始或正在进行
        //如果秒杀开始时间比现在晚,那就是还没开始
        if (promoModel.getStartTime().isAfterNow()) {
            promoModel.setStatus(1);
            //如果秒杀结束时间比现在早,那就是已经结束
        } else if (promoModel.getEndTime().isBeforeNow()) {
            promoModel.setStatus(3);
        }else {
            promoModel.setStatus(2);
        }
        return promoModel;
    }

    private PromoModel covertFromDataObject(PromoDO promoDO){
        if (promoDO==null) {
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO,promoModel);
        promoModel.setPromoItemPrice(new BigDecimal(promoDO.getPromoItemPrice()));
        promoModel.setStartTime(new DateTime(promoDO.getStartDate()));
        promoModel.setEndTime(new DateTime(promoDO.getEndDate()));
        return promoModel;
    }
}
