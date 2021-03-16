package com.liquid.hmily.order.service;


import cn.hutool.db.sql.Order;

import java.math.BigDecimal;

/**
 * 订单 Service
 *
 * @author Liquid
 */
public interface OmsOrderService {

    /**
     * 创建订单并且进行扣除账户余额支付，并进行库存扣减操作.
     *
     * @param count  购买数量
     * @param amount 支付金额
     * @return string string
     */
    String orderPay(Integer count, BigDecimal amount);


    /**
     * 更新订单状态.
     *
     * @param order 订单实体类
     */
    void updateOrderStatus(Order order);
}
