package com.miaoshaproject.service;

import com.miaoshaproject.service.model.PromoModel;

/**
 * @author bao
 * @version 1.00
 * @time 2020/5/16 19:49
 */
public interface PromoService {
    PromoModel getPromoByItemId(Integer itemId);
}
