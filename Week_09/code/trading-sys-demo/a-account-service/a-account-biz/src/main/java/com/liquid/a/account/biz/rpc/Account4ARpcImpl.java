package com.liquid.a.account.biz.rpc;

import com.liquid.a.account.api.rpc.Account4ARpc;
import com.liquid.a.account.biz.service.AccountService;
import com.liquid.common.account.api.dto.RMBAccountDTO;
import com.liquid.common.account.api.dto.USDAccountDTO;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A用户账户RPC实现类
 *
 * @author Liquid
 */
@DubboService
public class Account4ARpcImpl implements Account4ARpc {
    @Autowired
    private AccountService accountService;

    public boolean trade(USDAccountDTO usdAccountDTO, RMBAccountDTO rmbAccountDTO) {
        return accountService.trade(usdAccountDTO, rmbAccountDTO);
    }
}
