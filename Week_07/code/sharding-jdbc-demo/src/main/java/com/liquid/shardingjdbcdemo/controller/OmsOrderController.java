package com.liquid.shardingjdbcdemo.controller;

import com.liquid.shardingjdbcdemo.db.domain.OmsOrder;
import com.liquid.shardingjdbcdemo.service.OmsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 订单 Controller类
 *
 * @author Liquid
 */
@RestController
@RequestMapping("/oms")
public class OmsOrderController {
    @Autowired
    private OmsOrderService omsOrderService;


    @GetMapping("/list")
    public List<OmsOrder> listOmsOrder() {
        return omsOrderService.listOmsOrder();
    }

    @GetMapping("/update")
    public int update(Integer orderId) {
        return omsOrderService.updateOrder(orderId);
    }

    @GetMapping("/find")
    public OmsOrder find(Integer userId) {
        return omsOrderService.queryByUserId(userId);
    }
}
