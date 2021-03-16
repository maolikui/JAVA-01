package com.liquid.hmily.inventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liquid.hmily.inventory.api.dto.PmsProductSkuDTO;
import com.liquid.hmily.inventory.api.entity.PmsProductSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
// import org.apache.ibatis.annotations.Update;

/**
 * 订单Mapper类
 *
 * @author Liquid
 */
@Mapper
public interface InventoryMapper extends BaseMapper<PmsProductSku> {

    /**
     * 减库存
     * 此例将库存与锁库存的差值作为剩余库存
     *
     * @param pmsProductSkuDTO
     * @return
     */
    @Update("update pms_product_sku set stock = stock - #{count}," +
            " lock_stock= lock_stock + #{count} " +
            " where product_id =#{productId} and stock > 0  ")
    int decrease(PmsProductSkuDTO pmsProductSkuDTO);

    @Update("update pms_product_sku set " +
            " lock_stock = lock_stock - #{count} " +
            " where product_id =#{productId} and stock > 0 ")
    int confirm(PmsProductSkuDTO pmsProductSkuDTO);

    @Update("update pms_product_sku set stock = stock + #{count}," +
            " lock_stock= lock_stock - #{count} " +
            " where product_id =#{productId}  and lock_stock > 0 ")
    int cancel(PmsProductSkuDTO pmsProductSkuDTO);
}
