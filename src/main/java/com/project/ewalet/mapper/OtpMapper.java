package com.project.ewalet.mapper;

import com.project.ewalet.model.Otp;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface OtpMapper {
    final String findByCode = "SELECT * FROM otp WHERE code = #{code}";
    final String getAllActiveOtp = "SELECT * FROM otp where status = 1";
    final String insert = "INSERT INTO otp (id, user_id, code, STATUS, CREATED_AT ) " +
            "VALUES (#{id}, #{user_id}, #{code}, #{status}, #{created_at})";
    final String update = "UPDATE otp SET status = #{status} WHERE id = #{id} and user_id = #{user_id}";

    @Select(getAllActiveOtp)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "code", column = "CODE"),
            @Result(property = "status", column = "status"),
            @Result(property = "created_at", column = "created_at")
    })
    ArrayList<Otp> getAllActiveOtp();

    @Select(findByCode)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "code", column = "code"),
            @Result(property = "status", column = "status"),
            @Result(property = "created_at", column = "CREATED_AT")
    })
    Otp findByCode(String code);

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Otp otp);

    @Update(update)
    void update(Otp otp);
}
