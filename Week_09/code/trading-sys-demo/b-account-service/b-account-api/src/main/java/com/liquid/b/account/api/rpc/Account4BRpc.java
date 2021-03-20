package com.liquid.b.account.api.rpc;


import com.liquid.common.account.api.dto.RMBAccountDTO;
import com.liquid.common.account.api.dto.USDAccountDTO;
import org.dromara.hmily.annotation.Hmily;

/**
 * B Account service
 *
 * @author Liquid
 */
public interface Account4BRpc {
    @Hmily
    boolean trade(USDAccountDTO usdAccountDTO, RMBAccountDTO rmbAccountDTO);
}
