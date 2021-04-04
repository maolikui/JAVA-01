package com.liquid.redisson.lock.demo.dto;

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