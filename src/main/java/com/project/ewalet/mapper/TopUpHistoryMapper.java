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

    final String insert = "insert into topup_history (user_id, topup_balance, token, payment_method, status, created_at, file_upload_id) " +
            "values (#{user_id}, #{topup_balance}, #{token}, #{payment_method}, #{status}, #{created_at}, #{file_upload_id})";
    final String update = "update topup_history set created_at = #{created_at}, payment_method= #{payment_method}," +
            "status = #{status}, token =#{token}, topup_balance = #{topup_balance}, user_id = #{user_id}," +
            "file_upload_id = #{file_upload_id} where id = #{id}";
    final String updateTopUpHistory = "update topup_history set status = #{status}, file_upload_id = #{file_upload_id} where id = #{id}";
    final String updateStatus = "update topup_history set status = #{status} where user_id = #{user_id}";
    final String updateStatusById = "update topup_history set status = #{status} where id = #{id}";
    final String getLatestRecord = "select * from topup_history where user_id = #{user_id} and token = #{token}" +
            " and status = 0 and payment_method = 1 order by created_at desc limit 1";
    final String getLastHistoryByToken = "select * from topup_history where token = #{token} order by id desc limit 1";
    final String getTopupHistoryByUserId ="select * from topup_history where user_id = #{userid}";
    final String getTopupHistoryById = "select * from topup_history where id = #{id}";
    final String getTopupHistoryBanksByUserId = "select t.*, f.path, f.file_name, p.payment_type, p.name from topup_" +
            "history as t join files as f on t.file_upload_id = f.id join payment_method as p on " +
            "t.payment_method = p.id where t.user_id = #{user_id}";
    final String getTopupHistoryBanksWithoutFileByUserId = "select t.*, p.payment_type, p.name from topup_history " +
            "as t join payment_method as p on t.payment_method = p.id where t.user_id = #{user_id} and " +
            "p.payment_type = 1 and t.file_upload_id = 0";
    final String getTopupHistoryMerchantsByUserId = "select t.*, p.payment_type, p.name from topup_history as " +
            "t join payment_method as p on t.payment_method = p.id where t.user_id = #{user_id} and p.payment_type = 2";

    @Select(getTopupHistoryMerchantsByUserId)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "user_id", column = "USER_id"),
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
            @Result(property = "id", column = "id"),
            @Result(property = "user_id", column = "USER_id"),
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
            @Result(property = "id", column = "id"),
            @Result(property = "user_id", column = "USER_id"),
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
            @Result(property = "id", column = "id"),
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
            @Result(property = "id", column = "id"),
            @Result(property = "payment_method", column = "payment_method"),
            @Result(property = "status", column = "status"),
            @Result(property = "token", column = "token"),
            @Result(property = "topup_balance", column = "topup_balance"),
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "created_at", column = "created_at")
    })
    TopUpHistory getLastHistoryByToken(String token);

}
