package com.project.ewalet.mapper;

import com.project.ewalet.model.Otp;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface OtpMapper {
    final String findByCode = "select * from otp where code = #{code}";
    final String getAllActiveOtp = "select * from otp where status = 1";
    final String insert = "insert into otp (id, user_id, code, status, created_at ) " +
            "values (#{id}, #{user_id}, #{code}, #{status}, #{created_at})";
    final String update = "update otp set status = #{status} where id = #{id} and user_id = #{user_id}";

    @Select(getAllActiveOtp)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "code", column = "code"),
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
            @Result(property = "created_at", column = "created_at")
    })
    Otp findByCode(String code);

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(Otp otp);

    @Update(update)
    void update(Otp otp);
}
