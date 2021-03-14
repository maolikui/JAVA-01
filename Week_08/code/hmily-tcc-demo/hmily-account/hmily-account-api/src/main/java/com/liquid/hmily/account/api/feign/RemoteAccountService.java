package com.liquid.hmily.account.api.feign;

import com.liquid.hmily.account.api.constant.AccountServiceInfo;
import com.liquid.hmily.account.api.dto.AccountDTO;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Account service feign client
 *
 * @author Liquid
 */
@FeignClient(value = AccountServiceInfo.ACCOUNT_SERVICE)
public interface RemoteAccountService {

    /**
     * 库存扣减.
     *
     * @param accountDTO 实体对象
     * @return true 成功
     */
    @PostMapping("/account-service/account/payment")
    @Hmily
    Boolean payment(@RequestBody AccountDTO accountDTO);
}
