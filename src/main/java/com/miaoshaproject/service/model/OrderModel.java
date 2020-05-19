package com.miaoshaproject.service.model;

import java.math.BigDecimal;

/**
 * @author bao
 * @version 1.00
 * @time 2020/5/15 20:06
 */
public class OrderModel {
    //交易单号， 例如2019052100001212， 使⽤string类型
    private String id;
    //购买的⽤户id
    private Integer userId;
    //购买的商品id
    private Integer itemId;
    //若⾮空， 则表⽰是以秒杀商品⽅式下单
    private Integer promoId;
    //购买时商品的单价,若promoId⾮空， 则表⽰是以秒杀商品⽅式下单
    private BigDecimal itemPrice;

    //购买数量
    private Integer amount;
    //购买⾦额
    private BigDecimal orderPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }
}
