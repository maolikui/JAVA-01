package com.liquid.hmily.order.service.impl;

import com.liquid.hmily.account.api.dto.AccountDTO;
import com.liquid.hmily.account.api.feign.RemoteAccountService;
import com.liquid.hmily.inventory.api.dto.PmsProductSkuDTO;
import com.liquid.hmily.inventory.api.feign.RemoteInventoryService;
import com.liquid.hmily.order.api.entity.OmsOrder;
import com.liquid.hmily.order.api.enums.OrderStatusEnum;
import com.liquid.hmily.order.mapper.OmsOrderMapper;
import com.liquid.hmily.order.service.PaymentService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 支付服务实现类
 *
 * @author Liquid
 */
@Service
@SuppressWarnings("all")
public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);
    private final OmsOrderMapper orderMapper;
    private final RemoteAccountService accountService;
    private final RemoteInventoryService inventoryService;

    @Autowired(required = false)
    public PaymentServiceImpl(OmsOrderMapper orderMapper,
                              RemoteAccountService accountService,
                              RemoteInventoryService inventoryService) {
        this.orderMapper = orderMapper;
        this.accountService = accountService;
        this.inventoryService = inventoryService;
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    public void makePayment(OmsOrder order, Integer count) {
        //更改订单状态
        updateOrderStatus(order, OrderStatusEnum.PAYING);
        //支付,账户扣钱
        accountService.payment(buildAccountDTO(order));
        //扣钱成功后减库存
        //一般扣减真实库存
        inventoryService.decrease(buildInventoryDTO(count));
    }

    private AccountDTO buildAccountDTO(OmsOrder order) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAmount(order.getGoodsPrice());
        accountDTO.setUserId(order.getUserId());
        return accountDTO;
    }

    private PmsProductSkuDTO buildInventoryDTO(Integer count) {
        PmsProductSkuDTO pmsProductSkuDTO = new PmsProductSkuDTO();
        pmsProductSkuDTO.setCount(count);
        //为了测试，固定写1
        pmsProductSkuDTO.setProductId(1);
        return pmsProductSkuDTO;
    }

    public void confirmOrderStatus(OmsOrder order, Integer count) {
        updateOrderStatus(order, OrderStatusEnum.PAY_SUCCESS);
        LOGGER.info("=========进行订单confirm操作完成================");
    }

    public void cancelOrderStatus(OmsOrder order) {
        updateOrderStatus(order, OrderStatusEnum.PAY_FAIL);
        LOGGER.info("=========进行订单cancel操作完成================");
    }

    private void updateOrderStatus(OmsOrder order, OrderStatusEnum orderStatus) {
        order.setOrderStatus(orderStatus.getCode());
        orderMapper.update(order);
    }
}
