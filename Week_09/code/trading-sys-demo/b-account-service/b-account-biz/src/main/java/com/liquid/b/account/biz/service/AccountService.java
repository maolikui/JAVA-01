package com.liquid.b.account.biz.service;


import com.liquid.common.account.api.dto.RMBAccountDTO;
import com.liquid.common.account.api.dto.USDAccountDTO;

/**
 * Account Service.
 *
 * @author Liquid
 */
public interface AccountService {
    boolean trade(USDAccountDTO usdAccountDTO, RMBAccountDTO rmbAccountDTO);
}

