/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.liquid.hmily.order.service.impl;

import cn.hutool.db.sql.Order;
import com.liquid.hmily.order.api.entity.OmsOrder;
import com.liquid.hmily.order.api.enums.OrderStatusEnum;
import com.liquid.hmily.order.mapper.OmsOrderMapper;
import com.liquid.hmily.order.service.OmsOrderService;
import com.liquid.hmily.order.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 订单 Service 实现类
 *
 * @author Liquid
 */
@Service("orderService")
public class OmsOrderServiceImpl implements OmsOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OmsOrderServiceImpl.class);

    private final OmsOrderMapper orderMapper;

    private final PaymentService paymentService;

    @Autowired(required = false)
    public OmsOrderServiceImpl(OmsOrderMapper orderMapper, PaymentService paymentService) {
        this.orderMapper = orderMapper;
        this.paymentService = paymentService;
    }

    @Override
    public String orderPay(Integer count, BigDecimal amount) {
        //下单
        OmsOrder order = saveOrder(amount);
        long start = System.currentTimeMillis();
        //支付
        paymentService.makePayment(order, count);
        System.out.println("hmily-cloud分布式事务耗时：" + (System.currentTimeMillis() - start));
        return "success";
    }


    @Override
    public void updateOrderStatus(Order order) {
        // orderMapper.update(order);
    }

    private OmsOrder saveOrder(BigDecimal amount) {
        //一般可以先锁定库存,等付款后真实减库存
        //生成订单，写入数据库
        final OmsOrder order = buildOrder(amount);
        orderMapper.insert(order);
        return order;
    }

    private OmsOrder buildOrder(BigDecimal amount) {
        LOGGER.debug("构建订单对象");
        OmsOrder order = new OmsOrder();
        //demo中 表里面存的用户id为100
        order.setUserId(100);
        // order.setNumber(String.valueOf(IdWorkerUtils.getInstance().createUUID()));
        order.setOrderSn("111000");
        order.setOrderSn("1");
        order.setOrderStatus(OrderStatusEnum.NOT_PAY.getCode());
        order.setConsignee("Liquid");
        order.setMobile("18588888888");
        order.setAddress("SH");
        order.setGoodsPrice(amount);
        order.setFreightPrice(new BigDecimal("5"));
        order.setAddTime(new Date());
        return order;
    }
}
