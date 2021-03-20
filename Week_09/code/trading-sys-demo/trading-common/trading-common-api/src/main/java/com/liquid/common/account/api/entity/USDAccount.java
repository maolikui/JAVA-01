package com.liquid.common.account.api.entity;

import lombok.Data;

import java.util.Date;

/**
 * 美元账户类
 *
 * @author Liquid
 */
@Data
public class USDAccount {
    private Long id;
    private String userId;
    private Long balance;
    private Long freezeAmount;
    private Date createTime;
    private Date updateTime;
    private Integer deleted;
}