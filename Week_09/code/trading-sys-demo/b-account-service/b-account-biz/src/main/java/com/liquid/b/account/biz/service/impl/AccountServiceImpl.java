package com.liquid.b.account.biz.service.impl;

import com.liquid.b.account.biz.mapper.RMBAccountMapper;
import com.liquid.b.account.biz.mapper.USDAccountMapper;
import com.liquid.b.account.biz.service.AccountService;
import com.liquid.common.account.api.dto.RMBAccountDTO;
import com.liquid.common.account.api.dto.USDAccountDTO;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Account service.
 *
 * @author Liquid
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final RMBAccountMapper rmbAccountMapper;
    private final USDAccountMapper usdAccountMapper;

    @Autowired
    public AccountServiceImpl(RMBAccountMapper rmbAccountMapper, USDAccountMapper usdAccountMapper) {
        this.rmbAccountMapper = rmbAccountMapper;
        this.usdAccountMapper = usdAccountMapper;
    }

    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    @Transactional(rollbackFor = Exception.class)
    public boolean trade(USDAccountDTO usdAccountDTO, RMBAccountDTO rmbAccountDTO) {
        //人名币账户扣除7元
        rmbAccountMapper.decrease(rmbAccountDTO);
        //美元账户增加1美元
        usdAccountMapper.increase(usdAccountDTO);
        return Boolean.TRUE;
    }

    public Boolean confirm(USDAccountDTO usdAccountDTO, RMBAccountDTO rmbAccountDTO) {
        log.info("B用户人名币账户confirm方法执行");
        rmbAccountMapper.confirm(rmbAccountDTO);
        return Boolean.TRUE;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean cancel(USDAccountDTO usdAccountDTO, RMBAccountDTO rmbAccountDTO) {
        log.info("B用户人名币账户cancelInc方法执行");
        return rmbAccountMapper.cancel(rmbAccountDTO) > 0 && usdAccountMapper.cancelInc(usdAccountDTO) > 0;
    }


}
