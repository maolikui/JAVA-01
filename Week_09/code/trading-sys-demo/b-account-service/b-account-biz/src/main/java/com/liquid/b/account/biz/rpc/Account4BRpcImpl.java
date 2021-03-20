package com.liquid.b.account.biz.rpc;

import com.liquid.b.account.api.dto.RMBAccountDTO;
import com.liquid.b.account.api.dto.USDAccountDTO;
import com.liquid.b.account.api.rpc.Account4BRpc;
import com.liquid.b.account.biz.service.AccountService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * B用户账户RPC实现类
 *
 * @author Liquid
 */
@DubboService
public class Account4BRpcImpl implements Account4BRpc {
    @Autowired
    private AccountService accountService;

    public boolean trade(USDAccountDTO usdAccountDTO, RMBAccountDTO rmbAccountDTO) {
        return accountService.trade(usdAccountDTO, rmbAccountDTO);
    }
}
