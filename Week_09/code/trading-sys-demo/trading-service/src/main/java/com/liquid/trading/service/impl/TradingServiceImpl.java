package com.liquid.trading.service.impl;

import com.liquid.a.account.api.rpc.Account4ARpc;
import com.liquid.b.account.api.rpc.Account4BRpc;
import com.liquid.common.account.api.dto.RMBAccountDTO;
import com.liquid.common.account.api.dto.USDAccountDTO;
import com.liquid.trading.service.TradingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 交易业务
 *
 * @author Liquid
 */
@Service("tradingService")
@Slf4j
public class TradingServiceImpl implements TradingService {

    @DubboReference
    private Account4ARpc account4ARpc;
    @DubboReference
    private Account4BRpc account4BRpc;

    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public void makeTrade() {
        //业务流程设计为当一个用户账户扣账后，对应另一个账户余额增加
        //用户A美元账户减去1美元,人名币账户加上7元
        account4ARpc.trade(buildUSDAccountDTO(), buildRMBAccountDTO());

        //用户B人名币账户减去7元,美元账户加上1美元
        account4BRpc.trade(buildUSDAccountDTO(), buildRMBAccountDTO());
    }

    private RMBAccountDTO buildRMBAccountDTO() {
        RMBAccountDTO rmbAccountDTO = new RMBAccountDTO();
        rmbAccountDTO.setUserId(100);
        rmbAccountDTO.setAmount(new BigDecimal(7));
        return rmbAccountDTO;
    }

    private USDAccountDTO buildUSDAccountDTO() {
        USDAccountDTO usdAccountDTO = new USDAccountDTO();
        usdAccountDTO.setUserId(100);
        usdAccountDTO.setAmount(new BigDecimal(1));
        return usdAccountDTO;
    }

    public Boolean cancel() {
        log.info("交易系统方法cancel执行");
        return Boolean.TRUE;
    }

    public Boolean confirm() {
        log.info("交易系统方法confirm执行");
        return Boolean.TRUE;
    }
}
