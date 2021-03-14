package com.liquid.hmily.inventory.api.dto;

import lombok.Data;

/**
 * 库存
 *
 * @author Liquid
 */
@Data
public class PmsProductSkuDTO {
    private Integer productId;
    private Integer count;
}