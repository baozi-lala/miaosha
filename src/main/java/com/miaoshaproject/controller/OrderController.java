package com.miaoshaproject.controller;

import com.miaoshaproject.controller.viewobject.ItemVO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.OrderService;
import com.miaoshaproject.service.model.ItemModel;
import com.miaoshaproject.service.model.OrderModel;
import com.miaoshaproject.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author bao
 * @version 1.00
 * @time 2020/5/15 21:05
 */

@Controller("order")
@RequestMapping("/order")
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class OrderController extends BaseController {
    @Autowired
    private HttpServletRequest httpServletRequest;
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/createorder", method = {RequestMethod.POST},consumes =
            {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name="itemId") Integer itemId,
                                        @RequestParam(name="promoId",required = false) Integer promoId,
                                        @RequestParam(name="amount") Integer amount) throws BusinessException {
        Boolean isLogin=(Boolean)httpServletRequest.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()) {
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN, "⽤户还未登录， 不能下单");
        }
        UserModel userModel=(UserModel)httpServletRequest.getSession().getAttribute("LOGIN_USER");
        OrderModel orderModel=orderService.createOrder(userModel.getId(),itemId,promoId,amount);
        return CommonReturnType.create(null);
    }
}
