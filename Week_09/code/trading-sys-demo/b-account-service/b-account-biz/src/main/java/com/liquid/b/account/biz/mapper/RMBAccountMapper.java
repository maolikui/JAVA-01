package com.liquid.b.account.biz.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liquid.common.account.api.dto.AccountDTO;
import com.liquid.common.account.api.entity.RMBAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * RMB账户Mapper类
 *
 * @author Liquid
 */
@Mapper
public interface RMBAccountMapper extends BaseMapper<RMBAccount> {
    @Update("update rmb_account set balance = balance - #{amount}," +
            " freeze_amount= freeze_amount + #{amount} ,update_time = now()" +
            " where user_id =#{userId}  and  balance > 0  ")
    int decrease(AccountDTO accountDTO);

    @Update("update rmb_account set balance = balance + #{amount}," +
            "update_time = now()" + " where user_id =#{userId}  ")
    int increase(AccountDTO accountDTO);

    @Update("update rmb_account set " +
            " freeze_amount= freeze_amount - #{amount}" +
            " where user_id =#{userId}  and freeze_amount >0 ")
    int confirm(AccountDTO accountDTO);

    @Update("update rmb_account set balance = balance + #{amount}," +
            " freeze_amount= freeze_amount -  #{amount} " +
            " where user_id =#{userId}  and freeze_amount >0")
    int cancel(AccountDTO accountDTO);

    @Update("update rmb_account set " +
            " freeze_amount= freeze_amount - #{amount}" +
            " where user_id =#{userId}  and freeze_amount >0 ")
    int confirmInc(AccountDTO accountDTO);

    @Update("update rmb_account set balance = balance - #{amount}," +
            " where user_id =#{userId}")
    int cancelInc(AccountDTO accountDTO);

    @Select("select id,user_id,balance, freeze_amount from rmb_account where user_id =#{userId} limit 1")
    RMBAccount findByUserId(String userId);
}
