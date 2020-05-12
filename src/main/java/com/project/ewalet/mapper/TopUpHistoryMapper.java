package com.project.ewalet.mapper;

import com.project.ewalet.model.TopUpHistory;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TopUpHistoryMapper {

    final String insert = "INSERT INTO topup_history (user_id, topup_balance, token, payment_method, status, created_at) " +
            "VALUES (#{user_id}, #{topup_balance}, #{token}, #{payment_method}, #{status}, #{created_at})";
    final String updateStatus = "UPDATE topup_history SET status = #{status} WHERE user_id = #{user_id}";
    final String getLatestRecord = "SELECT * from topup_history where user_id = #{user_id} and token = #{token}" +
            " ORDER BY created_at DESC limit 1";
    final String getLastHistoryByToken = "SELECT * FROM TOPUP_HISTORY WHERE TOKEN = #{token} ORDER BY ID DESC LIMIT 1";

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(TopUpHistory topUpHistory);

    @Update(updateStatus)
    void updateStatus(int status, long user_id);

    @Select(getLatestRecord)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "payment_method", column = "payment_method"),
            @Result(property = "status", column = "status"),
            @Result(property = "token", column = "token"),
            @Result(property = "topup_balance", column = "topup_balance"),
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "created_at", column = "created_at")
    })
    TopUpHistory findLatestRecordByDateAndUserId(long user_id, String token);

    @Select(getLastHistoryByToken)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "payment_method", column = "payment_method"),
            @Result(property = "status", column = "status"),
            @Result(property = "token", column = "token"),
            @Result(property = "topup_balance", column = "topup_balance"),
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "created_at", column = "created_at")
    })
    TopUpHistory getLastHistoryByToken(String token);

}
