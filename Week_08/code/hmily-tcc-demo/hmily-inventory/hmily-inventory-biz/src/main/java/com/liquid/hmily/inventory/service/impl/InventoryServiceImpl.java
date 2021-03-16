package com.liquid.hmily.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.liquid.hmily.inventory.api.dto.PmsProductSkuDTO;
import com.liquid.hmily.inventory.api.entity.PmsProductSku;
import com.liquid.hmily.inventory.mapper.InventoryMapper;
import com.liquid.hmily.inventory.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * InventoryServiceImpl.
 *
 * @author Liquid
 */
@Service("inventoryService")
@Slf4j
public class InventoryServiceImpl implements InventoryService {


    private final InventoryMapper inventoryMapper;

    @Autowired(required = false)
    public InventoryServiceImpl(InventoryMapper inventoryMapper) {
        this.inventoryMapper = inventoryMapper;
    }

    /**
     * 扣减库存操作
     *
     * @param pmsProductSkuDTO 库存DTO对象
     * @return true
     */
    @Override
    @HmilyTCC(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    public Boolean decrease(PmsProductSkuDTO pmsProductSkuDTO) {
        log.info("==========try扣减库存decrease===========");
        inventoryMapper.decrease(pmsProductSkuDTO);
        return true;
    }


    /**
     * 获取商品库存信息.
     *
     * @param productId 商品id
     * @return PmsProductSku
     */
    @Override
    public PmsProductSku findByProductId(String productId) {
        QueryWrapper<PmsProductSku> wrapper = new QueryWrapper<>();
        wrapper.eq("productId", productId);
        return inventoryMapper.selectOne(wrapper);
    }

    public Boolean confirmMethod(PmsProductSkuDTO pmsProductSkuDTO) {
        log.info("==========confirmMethod库存确认方法===========");
        return inventoryMapper.confirm(pmsProductSkuDTO) > 0;
    }

    public Boolean cancelMethod(PmsProductSkuDTO pmsProductSkuDTO) {
        log.info("==========cancelMethod库存取消方法===========");
        return inventoryMapper.cancel(pmsProductSkuDTO) > 0;
    }
}
