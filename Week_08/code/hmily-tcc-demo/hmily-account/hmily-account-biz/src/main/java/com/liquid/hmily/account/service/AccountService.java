package com.liquid.hmily.account.service;


import com.liquid.hmily.account.api.dto.AccountDTO;
import com.liquid.hmily.account.api.entity.Account;

/**
 * Account Service.
 *
 * @author Liquid
 */
public interface AccountService {

    /**
     * 扣款支付.
     *
     * @param accountDTO 参数dto
     * @return true boolean
     */
    boolean payment(AccountDTO accountDTO);


    /**
     * 获取用户账户信息.
     *
     * @param userId 用户id
     * @return Account account
     */
    Account findByUserId(String userId);
}
