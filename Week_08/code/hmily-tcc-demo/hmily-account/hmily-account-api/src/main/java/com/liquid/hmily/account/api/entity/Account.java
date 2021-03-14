package com.liquid.hmily.account.api.entity;

import lombok.Data;

import java.util.Date;

/**
 * 账户类
 *
 * @author Liquid
 */
@Data
public class Account {
    private Long id;

    private String userId;

    private Long balance;

    private Long freezeAmount;

    private Date createTime;

    private Date updateTime;
}