package com.liquid.common.account.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Account DTO
 *
 * @author Liquid
 */
@Data
public class AccountDTO implements Serializable {

    private static final long serialVersionUID = 546177863157952382L;

    private Integer userId;
    private BigDecimal amount;
}
