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
    final String getTopupHistoryByUserId = "SELECT * FROM TOPUP_HISTORY WHERE USER_ID = #{userId}";
    final String getTopupHistoryById = "SELECT * FROM TOPUP_HISTORY WHERE ID = #{id}";
    final String getTopupHistoryBanksByUserId = "SELECT T.*, F.PATH, F.FILE_NAME, P.PAYMENT_TYPE, P.NAME FROM TOPUP_" +
            "HISTORY AS T JOIN FILES AS F ON T.FILE_UPLOAD_ID = F.ID JOIN PAYMENT_METHOD AS P ON " +
            "T.PAYMENT_METHOD = P.ID WHERE T.USER_ID = #{user_id}";
    final String getTopupHistoryBanksWithoutFileByUserId = "SELECT T.*, P.PAYMENT_TYPE, P.NAME FROM TOPUP_HISTORY " +
            "AS T JOIN PAYMENT_METHOD AS P ON T.PAYMENT_METHOD = P.ID WHERE T.USER_ID = #{user_id} AND " +
            "P.PAYMENT_TYPE = 1 AND T.FILE_UPLOAD_ID = 0";
    final String getTopupHistoryMerchantsByUserId = "SELECT T.*, P.PAYMENT_TYPE, P.NAME FROM TOPUP_HISTORY AS " +
            "T JOIN PAYMENT_METHOD AS P ON T.PAYMENT_METHOD = P.ID WHERE T.USER_ID = #{user_id} AND P.PAYMENT_TYPE = 2";

    @Select(getTopupHistoryMerchantsByUserId)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "user_id", column = "USER_ID"),
            @Result(property = "topup_balance", column = "TOPUP_BALANCE"),
            @Result(property = "token", column = "TOKEN"),
            @Result(property = "payment_type", column = "PAYMENT_TYPE"),
            @Result(property = "name", column = "NAME"),
            @Result(property = "status", column = "STATUS"),
            @Result(property = "path", column = "PATH"),
            @Result(property = "created_at", column = "CREATED_AT")
    })
    List<TopUpHistoryPayload> getTopupHistoryMerchantsByUserId(long user_id);

    @Select(getTopupHistoryBanksWithoutFileByUserId)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "user_id", column = "USER_ID"),
            @Result(property = "topup_balance", column = "TOPUP_BALANCE"),
            @Result(property = "token", column = "TOKEN"),
            @Result(property = "payment_type", column = "PAYMENT_TYPE"),
            @Result(property = "name", column = "NAME"),
            @Result(property = "status", column = "STATUS"),
            @Result(property = "path", column = "PATH"),
            @Result(property = "created_at", column = "CREATED_AT")
    })
    List<TopUpHistoryPayload> getTopupHistoryBanksWithoutFileByUserId(long user_id);

    @Select(getTopupHistoryBanksByUserId)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "user_id", column = "USER_ID"),
            @Result(property = "topup_balance", column = "TOPUP_BALANCE"),
            @Result(property = "token", column = "TOKEN"),
            @Result(property = "payment_type", column = "PAYMENT_TYPE"),
            @Result(property = "name", column = "NAME"),
            @Result(property = "status", column = "STATUS"),
            @Result(property = "path", column = "PATH"),
            @Result(property = "created_at", column = "CREATED_AT")
    })
    List<TopUpHistoryPayload> getTopupHistoryBanksByUserId(long user_id);

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
            @Result(property = "id", column = "ID"),
            @Result(property = "payment_method", column = "payment_method"),
            @Result(property = "status", column = "status"),
            @Result(property = "token", column = "token"),
            @Result(property = "topup_balance", column = "topup_balance"),
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "created_at", column = "created_at"),
            @Result(property = "file_upload_id", column = "file_upload_id")
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
