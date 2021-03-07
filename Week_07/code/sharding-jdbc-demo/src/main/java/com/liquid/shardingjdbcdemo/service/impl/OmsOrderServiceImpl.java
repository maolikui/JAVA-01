package com.liquid.shardingjdbcdemo.service.impl;

import com.liquid.shardingjdbcdemo.db.dao.OmsOrderMapper;
import com.liquid.shardingjdbcdemo.db.domain.OmsOrder;
import com.liquid.shardingjdbcdemo.db.domain.OmsOrderExample;
import com.liquid.shardingjdbcdemo.service.OmsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单 Service 实现类
 *
 * @author Liquid
 */
@Service
public class OmsOrderServiceImpl implements OmsOrderService {
    @Autowired
    private OmsOrderMapper omsOrderMapper;

    @Override
    public List<OmsOrder> listOmsOrder() {
        return omsOrderMapper.selectByExample(new OmsOrderExample());
    }

    @Override
    public int updateOrder(int orderId) {
        OmsOrder omsOrder = new OmsOrder();
        omsOrder.setId(orderId);
        omsOrder.setAddress("beijing");
        return omsOrderMapper.updateByPrimaryKeySelective(omsOrder);
    }

    @Override
    public OmsOrder queryByUserId(int userId) {
        OmsOrderExample example = new OmsOrderExample();
        example.createCriteria().andUserIdEqualTo(userId);
        return omsOrderMapper.selectOneByExample(example);
    }

}
