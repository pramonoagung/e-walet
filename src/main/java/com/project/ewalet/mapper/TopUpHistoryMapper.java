package com.project.ewalet.mapper;

import com.project.ewalet.model.TopUpHistory;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface TopUpHistoryMapper {

    final String insert = "INSERT INTO topup_history (user_id, topup_balance, token, payment_method, status, created_at) " +
            "VALUES (#{user_id}, #{topup_balance}, #{token}, #{payment_method}, #{status}, #{created_at})";
    final String updateStatus = "UPDATE topup_history SET status = #{status} WHERE user_id = #{user_id}";
    final String updateStatusById = "UPDATE TOPUP_HISTORY SET STATUS = #{status} WHERE ID = #{id}";
    final String getLatestRecord = "SELECT * from topup_history where user_id = #{user_id} and token = #{token}" +
            " ORDER BY created_at DESC limit 1";
    final String getLastHistoryByToken = "SELECT * FROM TOPUP_HISTORY WHERE TOKEN = #{token} ORDER BY ID DESC LIMIT 1";
    final String getTopupHistoryByUserId = "SELECT * FROM TOPUP_HISTORY WHERE USER_ID = #{userId}";
    final String getTopupHistoryById = "SELECT * FROM TOPUP_HISTORY WHERE ID = #{id}";

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(TopUpHistory topUpHistory);

    @Update(updateStatus)
    void updateStatus(int status, long user_id);

    @Update(updateStatusById)
    void updateStatusById(int status, long id);

    @Select(getTopupHistoryById)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "payment_method", column = "PAYMENT_METHOD"),
            @Result(property = "status", column = "STATUS"),
            @Result(property = "token", column = "TOKEN"),
            @Result(property = "topup_balance", column = "TOPUP_BALANCE"),
            @Result(property = "user_id", column = "USER_ID"),
            @Result(property = "created_at", column = "CREATED_AT")
    })
    ArrayList<TopUpHistory> getTopUpHistoryById(long id);

    @Select(getTopupHistoryByUserId)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "payment_method", column = "payment_method"),
            @Result(property = "status", column = "status"),
            @Result(property = "token", column = "token"),
            @Result(property = "topup_balance", column = "topup_balance"),
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "created_at", column = "created_at")
    })
    ArrayList<TopUpHistory> getTopUpHistoryByUserId(long user_id);

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
