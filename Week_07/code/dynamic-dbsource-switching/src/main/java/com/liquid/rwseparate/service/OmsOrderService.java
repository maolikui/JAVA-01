package com.liquid.rwseparate.service;

import com.liquid.rwseparate.db.domain.OmsOrder;

import java.util.List;

/**
 * 订单Service 接口类
 *
 * @author Liquid
 */
public interface OmsOrderService {

    List<OmsOrder> listOmsOrder();

    int updateOrder(int orderId);

    OmsOrder queryByUserId(int userId);

}
