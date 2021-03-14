package com.liquid.hmily.inventory.controller;

import com.liquid.hmily.inventory.api.dto.PmsProductSkuDTO;
import com.liquid.hmily.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 库存 Controller
 *
 * @author Liquid
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @RequestMapping("/decrease")
    public Boolean decrease(@RequestBody PmsProductSkuDTO pmsProductSkuDTO) {
        return inventoryService.decrease(pmsProductSkuDTO);
    }
}
