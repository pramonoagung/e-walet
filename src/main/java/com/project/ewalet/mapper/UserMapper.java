package com.project.ewalet.mapper;

import com.project.ewalet.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    final String getAll = "SELECT * FROM user";
    final String getById = "SELECT * FROM user WHERE ID = #{ID}";
    final String getByName = "SELECT * FROM user WHERE NAME = #{name}";
    final String deleteById = "DELETE from user WHERE ID = #{id}";
    final String insert = "INSERT INTO user (NAME, BRANCH, PERCENTAGE, PHONE, EMAIL ) VALUES (#{name}, #{branch}, #{percentage}, #{phone}, #{email})";

    final String update = "UPDATE user SET EMAIL = #{email}, password = #{password}, first_NAME = #{first_name}, " +
            "last_name = #{last_name}, status = #{status}, PHONE_number = #{phone_number} WHERE ID = #{id}";

    final String login = "UPDATE user SET LOGIN_STATUS = TRUE WHERE ID = #{id}";
    final String logout = "UPDATE user SET LOGIN_STATUS = FALSE WHERE ID = #{id}";
    final String auth = "SELECT * FROM user WHERE EMAIL = #{email} AND PASSWORD = #{password}";

    final String findByPhoneNumber = "SELECT * FROM user WHERE PHONE_NUMBER = #{phone_number}";
    final String findByEmail = "SELECT * FROM user WHERE EMAIL = #{email}";
    final String findByEmailAndPhone = "SELECT * FROM user WHERE EMAIL = #{email} and phone_number = #{phone_number}";
    final String save = "INSERT INTO user (EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, PHONE_NUMBER, STATUS, CREATED_AT ) " +
            "VALUES (#{email}, #{password}, #{first_name}, #{last_name}, #{phone_number}, #{status}, #{created_at})";
    final String updateToken = "UPDATE user SET TOKEN = #{token} WHERE ID = #{id}";

    @Select(findByPhoneNumber)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "email", column = "EMAIL"),
            @Result(property = "password", column = "PASSWORD"),
            @Result(property = "first_name", column = "FIRST_NAME"),
            @Result(property = "last_name", column = "LAST_NAME"),
            @Result(property = "phone_number", column = "PHONE_NUMBER"),
            @Result(property = "status", column = "STATUS"),
            @Result(property = "created_at", column = "CREATED_AT")
    })
    User findByPhoneNumber(String phone_number);

    @Select(findByEmail)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "email", column = "EMAIL"),
            @Result(property = "password", column = "PASSWORD"),
            @Result(property = "first_name", column = "FIRST_NAME"),
            @Result(property = "last_name", column = "LAST_NAME"),
            @Result(property = "phone_number", column = "PHONE_NUMBER"),
            @Result(property = "token", column = "TOKEN"),
            @Result(property = "status", column = "STATUS"),
            @Result(property = "created_at", column = "CREATED_AT")
    })
    User findByEmail(String email);

    @Select(findByEmailAndPhone)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "email", column = "EMAIL"),
            @Result(property = "password", column = "PASSWORD"),
            @Result(property = "first_name", column = "FIRST_NAME"),
            @Result(property = "last_name", column = "LAST_NAME"),
            @Result(property = "phone_number", column = "PHONE_NUMBER"),
            @Result(property = "token", column = "TOKEN"),
            @Result(property = "status", column = "STATUS"),
            @Result(property = "created_at", column = "CREATED_AT")
    })
    User findByEmailAndPhone(String email, String phone_number);

    @Insert(save)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(User user);

    @Update(updateToken)
    void updateToken(String token, long id);

    @Select(getAll)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "email", column = "EMAIL"),
            @Result(property = "password", column = "PASSWORD"),
            @Result(property = "name", column = "NAME"),
            @Result(property = "loginStatus", column = "LOGIN_STATUS")
    })
    List<User> getAll();

    @Select(getById)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "email", column = "EMAIL"),
            @Result(property = "password", column = "PASSWORD"),
            @Result(property = "name", column = "NAME"),
            @Result(property = "loginStatus", column = "LOGIN_STATUS")
    })
    User getById(long id);

    @Select(getByName)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "email", column = "EMAIL"),
            @Result(property = "password", column = "PASSWORD"),
            @Result(property = "name", column = "NAME"),
            @Result(property = "loginStatus", column = "LOGIN_STATUS")
    })
    User getByName(String name);

    @Select(auth)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "email", column = "EMAIL"),
            @Result(property = "password", column = "PASSWORD"),
            @Result(property = "name", column = "NAME"),
            @Result(property = "loginStatus", column = "LOGIN_STATUS")
    })
    User authValidation(String email, String password);

    @Update(login)
    void login(Long id);

    @Update(logout)
    void logout(Long id);

    @Update(update)
    void update(User user);

    @Delete(deleteById)
    void delete(int id);

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

}

