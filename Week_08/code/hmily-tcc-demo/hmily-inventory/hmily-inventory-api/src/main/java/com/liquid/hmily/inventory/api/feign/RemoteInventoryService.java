package com.liquid.hmily.inventory.api.feign;

import com.liquid.hmily.inventory.api.constant.InventoryServiceInfo;
import com.liquid.hmily.inventory.api.dto.PmsProductSkuDTO;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Inventory service feign client
 *
 * @author Liquid
 */
@FeignClient(value = InventoryServiceInfo.INVENTORY_SERVICE)
public interface RemoteInventoryService {

    /**
     * 库存扣减.
     *
     * @param pmsProductSkuDTO 实体对象
     * @return true 成功
     */
    @PostMapping("/inventory-service/inventory/decrease")
    @Hmily
    Boolean decrease(@RequestBody PmsProductSkuDTO pmsProductSkuDTO);
}
