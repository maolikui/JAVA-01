package com.liquid.hmily.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liquid.hmily.account.api.dto.AccountDTO;
import com.liquid.hmily.account.api.entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 账户Mapper类
 *
 * @author Liquid
 */
@Mapper
public interface AccountMapper extends BaseMapper<Account> {
    @Update("update account set balance = balance - #{amount}," +
            " freeze_amount= freeze_amount + #{amount} ,update_time = now()" +
            " where user_id =#{userId}  and  balance > 0  ")
    int update(AccountDTO accountDTO);

    @Select("select id,user_id,balance, freeze_amount from account where user_id =#{userId} limit 1")
    Account findByUserId(String userId);
}
