package com.liquid.hmily.order.service;


import com.liquid.hmily.order.api.entity.OmsOrder;

/**
 * 模拟支付服务
 *
 * @author Liquid
 */
public interface PaymentService {

    /**
     * 订单支付.
     *
     * @param order 订单实体
     * @param count 固定物品的数量
     */
    void makePayment(OmsOrder order, Integer count);
}
