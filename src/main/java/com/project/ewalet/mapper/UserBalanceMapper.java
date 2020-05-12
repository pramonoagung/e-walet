package com.project.ewalet.mapper;

import com.project.ewalet.model.UserBalance;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


@Repository
@Mapper
public interface UserBalanceMapper {

    final String findByUserId = "SELECT * FROM user_balance where user_id = #{user_id}";
    final String updateBalance = "UPDATE user_balance SET balance = #{balance} WHERE user_id = #{user_id}";

    @Select(findByUserId)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "balance", column = "balance")
    })
    UserBalance findByUserId(long user_id);

    @Update(updateBalance)
    void updateUserBalance(long balance, long user_id);

}
