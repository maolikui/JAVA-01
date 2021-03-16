package com.liquid.hmily.order.controller;

import com.liquid.hmily.order.service.OmsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 订单 Controller
 *
 * @author Liquid
 */
@RestController
@RequestMapping("/order")
public class OmsOrderController {

    private final OmsOrderService orderService;

    @Autowired
    public OmsOrderController(OmsOrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 模拟一键下单
     *
     * @param count
     * @param amount
     * @return
     */
    @PostMapping(value = "/orderPay")
    public String orderPay(@RequestParam(value = "count") Integer count,
                           @RequestParam(value = "amount") BigDecimal amount) {
        return orderService.orderPay(count, amount);
    }

}
