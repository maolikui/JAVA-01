package com.liquid.hmily.account.service.impl;

import com.liquid.hmily.account.api.dto.AccountDTO;
import com.liquid.hmily.account.api.entity.Account;
import com.liquid.hmily.account.mapper.AccountMapper;
import com.liquid.hmily.account.service.AccountService;
import com.liquid.hmily.inventory.api.feign.RemoteInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Account service.
 *
 * @author Liquid
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {
    /**
     * logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

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
    public boolean payment(final AccountDTO accountDTO) {
        LOGGER.info("============执行try付款接口===============");
        accountMapper.update(accountDTO);
        return Boolean.TRUE;
    }


    @Override
    public Account findByUserId(final String userId) {
        return accountMapper.findByUserId(userId);
    }
}
