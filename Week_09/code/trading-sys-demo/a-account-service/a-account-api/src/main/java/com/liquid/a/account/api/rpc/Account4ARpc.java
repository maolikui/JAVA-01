package com.liquid.a.account.api.rpc;


import com.liquid.common.account.api.dto.RMBAccountDTO;
import com.liquid.common.account.api.dto.USDAccountDTO;
import org.dromara.hmily.annotation.Hmily;

/**
 * A Account service
 *
 * @author Liquid
 */
public interface Account4ARpc {
    @Hmily
    boolean trade(USDAccountDTO usdAccountDTO, RMBAccountDTO rmbAccountDTO);
}
