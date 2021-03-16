package com.liquid.hmily.account.service.impl;

import com.liquid.hmily.account.api.dto.AccountDTO;
import com.liquid.hmily.account.api.entity.Account;
import com.liquid.hmily.account.mapper.AccountMapper;
import com.liquid.hmily.account.service.AccountService;
import com.liquid.hmily.inventory.api.feign.RemoteInventoryService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Account service.
 *
 * @author Liquid
 */
@Service("accountService")
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;

    private final RemoteInventoryService remoteInventoryService;

    /**
     * Instantiates a new Account service.
     *
     * @param accountMapper the account mapper
     */
    @Autowired(required = false)
    public AccountServiceImpl(final AccountMapper accountMapper, final RemoteInventoryService remoteInventoryService) {
        this.accountMapper = accountMapper;
        this.remoteInventoryService = remoteInventoryService;
    }

    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public boolean payment(final AccountDTO accountDTO) {
        log.info("============执行try付款接口===============");
        accountMapper.update(accountDTO);
        return Boolean.TRUE;
    }

    /**
     * Confirm boolean.
     *
     * @param accountDTO the account dto
     * @return the boolean
     */
    public boolean confirm(final AccountDTO accountDTO) {
        log.info("============执行confirm 付款接口===============");
        return accountMapper.confirm(accountDTO) > 0;
    }


    /**
     * Cancel boolean.
     *
     * @param accountDTO the account dto
     * @return the boolean
     */
    public boolean cancel(final AccountDTO accountDTO) {
        log.info("============执行cancel 付款接口===============");
        return accountMapper.cancel(accountDTO) > 0;
    }

    @Override
    public Account findByUserId(final String userId) {
        return accountMapper.findByUserId(userId);
    }
}
