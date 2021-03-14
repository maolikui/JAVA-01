package com.liquid.hmily.inventory.api.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 库存
 *
 * @author Liquid
 */
@Data
public class PmsProductSku {
    private Integer id;

    private Integer productId;

    private String specifications;

    private BigDecimal price;

    private Integer stock;

    private Integer lockStock;

    private String url;

    private Date addTime;

    private Date updateTime;

    private Boolean deleted;
}