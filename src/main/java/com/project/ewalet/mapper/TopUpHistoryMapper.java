package com.project.ewalet.mapper;

import com.project.ewalet.model.TopUpHistory;
import com.project.ewalet.model.payload.TopUpHistoryPayload;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Mapper
public interface TopUpHistoryMapper {

    final String insert = "INSERT INTO topup_history (user_id, topup_balance, token, payment_method, status, created_at, FILE_UPLOAD_ID) " +
            "VALUES (#{user_id}, #{topup_balance}, #{token}, #{payment_method}, #{status}, #{created_at}, #{file_upload_id})";
    final String update = "UPDATE topup_history SET created_at = #{created_at}, payment_method= #{payment_method}," +
            "status = #{status}, token =#{token}, topup_balance = #{topup_balance}, user_id = #{user_id}," +
            "file_upload_id = #{file_upload_id} WHERE id = #{id}";
    final String updateTopUpHistory = "UPDATE topup_history SET status = #{status}, file_upload_id = #{file_upload_id} WHERE id = #{id}";
    final String updateStatus = "UPDATE topup_history SET status = #{status} WHERE user_id = #{user_id}";
    final String updateStatusById = "UPDATE TOPUP_HISTORY SET STATUS = #{status} WHERE ID = #{id}";
    final String getLatestRecord = "SELECT * from topup_history where user_id = #{user_id} and token = #{token}" +
            " and status = 0 and payment_method = 1 ORDER BY created_at DESC limit 1";
    final String getLastHistoryByToken = "SELECT * FROM TOPUP_HISTORY WHERE TOKEN = #{token} ORDER BY ID DESC LIMIT 1";
    final String getTopupHistoryByUserId = "SELECT T.*, F.PATH, F.FILE_NAME, P.PAYMENT_TYPE, P.NAME FROM TOPUP_HISTORY AS T " +
            "JOIN PAYMENT_METHOD AS P ON T.PAYMENT_METHOD = P.ID LEFT JOIN FILES AS F ON T.FILE_UPLOAD_ID = F.ID WHERE T.USER_ID = #{userId}";
    final String getTopupHistoryById = "SELECT * FROM TOPUP_HISTORY WHERE ID = #{id}";

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(TopUpHistory topUpHistory);

    @Update(updateTopUpHistory)
    void updateTopUpHistory(long id, int status, int file_upload_id);

    @Update(updateStatus)
    void updateStatus(int status, long user_id);

    @Update(updateStatusById)
    void updateStatusById(int status, long id);

    @Select(getTopupHistoryById)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "payment_method", column = "payment_method"),
            @Result(property = "status", column = "status"),
            @Result(property = "token", column = "token"),
            @Result(property = "topup_balance", column = "topup_balance"),
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "created_at", column = "created_at"),
            @Result(property = "file_upload_id", column = "file_upload_id")
    })
    TopUpHistory getTopUpHistoryById(long id);

    @Select(getTopupHistoryByUserId)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "topup_balance", column = "topup_balance"),
            @Result(property = "token", column = "token"),
            @Result(property = "payment_method", column = "payment_method"),
            @Result(property = "name", column = "name"),
            @Result(property = "status", column = "status"),
            @Result(property = "path", column = "path"),
            @Result(property = "created_at", column = "created_at")
    })
    ArrayList<TopUpHistoryPayload> getTopUpHistoryByUserId(long user_id);

    @Select(getLatestRecord)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "payment_method", column = "payment_method"),
            @Result(property = "status", column = "status"),
            @Result(property = "token", column = "token"),
            @Result(property = "topup_balance", column = "topup_balance"),
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "created_at", column = "created_at"),
            @Result(property = "file_upload_id", column = "file_upload_id")
    })
    TopUpHistory findLatestRecordByDateTokenAndUserId(long user_id, String token);

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
