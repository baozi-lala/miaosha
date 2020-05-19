package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.OrderModel;

/**
 * @author bao
 * @version 1.00
 * @time 2020/5/15 20:10
 */
public interface OrderService {
    OrderModel createOrder(Integer userId, Integer itemId,  Integer promoId, Integer amount) throws BusinessException;
}
