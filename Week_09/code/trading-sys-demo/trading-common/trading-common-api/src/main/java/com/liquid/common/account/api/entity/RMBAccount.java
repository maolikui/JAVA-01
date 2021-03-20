package com.liquid.common.account.api.entity;

import lombok.Data;

import java.util.Date;

/**
 * 人民币账户类
 *
 * @author Liquid
 */
@Data
public class RMBAccount {
    private Long id;
    private String userId;
    private Long balance;
    private Long freezeAmount;
    private Date createTime;
    private Date updateTime;
    private Integer deleted;
}