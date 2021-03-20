package com.liquid.b.account.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liquid.common.account.api.dto.AccountDTO;
import com.liquid.common.account.api.entity.RMBAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * USD账户Mapper类
 *
 * @author Liquid
 */
@Mapper
public interface USDAccountMapper extends BaseMapper<RMBAccount> {
    @Update("update usd_account set balance = balance - #{amount}," +
            " freeze_amount= freeze_amount + #{amount} ,update_time = now()" +
            " where user_id =#{userId}  and  balance > 0  ")
    int decrease(AccountDTO accountDTO);

    @Update("update usd_account set balance = balance + #{amount}," +
            "update_time = now()" + " where user_id =#{userId}  ")
    int increase(AccountDTO accountDTO);

    @Update("update usd_account set " +
            " freeze_amount= freeze_amount - #{amount}" +
            " where user_id =#{userId}  and freeze_amount >0 ")
    int confirmDec(AccountDTO accountDTO);

    @Update("update usd_account set balance = balance + #{amount}," +
            " freeze_amount= freeze_amount -  #{amount} " +
            " where user_id =#{userId}  and freeze_amount >0")
    int cancelDec(AccountDTO accountDTO);

    @Update("update usd_account set balance = balance - #{amount}," +
            "update_time = now()" + " where user_id =#{userId}  ")
    int cancelInc(AccountDTO accountDTO);

    @Select("select id,user_id,balance, freeze_amount from usd_account where user_id =#{userId} limit 1")
    RMBAccount findByUserId(String userId);
}
