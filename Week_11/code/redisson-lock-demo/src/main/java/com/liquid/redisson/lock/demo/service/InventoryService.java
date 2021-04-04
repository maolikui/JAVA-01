package com.liquid.redisson.lock.demo.service;


import com.liquid.redisson.lock.demo.dto.PmsProductSkuDTO;
import com.liquid.redisson.lock.demo.entity.PmsProductSku;

/**
 * The interface Inventory service.
 *
 * @author Liquid
 */
public interface InventoryService {

    /**
     * 扣减库存操作.
     *
     * @param pmsProductSkuDTO 库存DTO对象
     * @return true boolean
     */
    Boolean decrease(PmsProductSkuDTO pmsProductSkuDTO);

    /**
     * 获取商品库存信息.
     *
     * @param productId 商品id
     * @return PmsProductSku pms product sku
     */
    PmsProductSku findByProductId(String productId);
}
