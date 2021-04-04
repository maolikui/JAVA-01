package com.liquid.redisson.lock.demo.controller;

import com.liquid.redisson.lock.demo.dto.PmsProductSkuDTO;
import com.liquid.redisson.lock.demo.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/decrease")
    public Boolean decrease(@RequestBody PmsProductSkuDTO pmsProductSkuDTO) {
        return inventoryService.decrease(pmsProductSkuDTO);
    }
}
