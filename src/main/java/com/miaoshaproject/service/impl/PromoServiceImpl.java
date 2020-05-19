package com.miaoshaproject.service.impl;

import com.miaoshaproject.dao.PromoDOMapper;
import com.miaoshaproject.dataobject.PromoDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.service.PromoService;
import com.miaoshaproject.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bao
 * @version 1.00
 * @time 2020/5/16 19:51
 */
@Service
public class PromoServiceImpl implements PromoService{
    @Autowired
    private PromoDOMapper promoDOMapper;
    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        PromoDO promoDO=promoDOMapper.selectByItemId(itemId);
        PromoModel promoModel=this.convertFromDataObject(promoDO);
        if(promoModel==null){
            return null;
        }
        //判断当前时间是否秒杀活动即将开始或正在进⾏
        DateTime now=new DateTime();
        if(promoModel.getStartDate().isAfterNow()){
            promoModel.setStatus(1);
        }else if(promoModel.getEndDate().isBeforeNow()){
            promoModel.setStatus(3);
        }else{
            promoModel.setStatus(2);
        }
        return promoModel;
    }
    public PromoModel convertFromDataObject(PromoDO promoDO){
        if(promoDO==null)return null;
        PromoModel promoModel=new PromoModel();
        BeanUtils.copyProperties(promoDO,promoModel);
        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));
        return promoModel;

    }
}
