package com.liquid.hmily.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liquid.hmily.order.api.entity.OmsOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * 订单Mapper类
 *
 * @author Liquid
 */
@Mapper
public interface OmsOrderMapper extends BaseMapper<OmsOrder> {
    @Update("update `oms_order` set order_status = #{orderStatus}  where order_sn = #{orderSn}")
    int update(OmsOrder order);
}
