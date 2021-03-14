package com.liquid.hmily.order.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单类
 *
 * @author Liquid
 */
@Data
public class OmsOrder implements Serializable {
    private static final long serialVersionUID = -9031416227715631129L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String orderSn;
    private Short orderStatus;
    private String consignee;
    private String mobile;
    private String address;
    private BigDecimal goodsPrice;
    private BigDecimal freightPrice;
    private String shipSn;
    private String shipChannel;
    private Date shipTime;
    private BigDecimal refundAmount;
    private String refundType;
    private String refundContent;
    private Date refundTime;
    private Date confirmTime;
    private Date endTime;
    private Date addTime;
    private Date updateTime;
    private Boolean deleted;
}