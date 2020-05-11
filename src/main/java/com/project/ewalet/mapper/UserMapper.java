package com.project.ewalet.mapper;

import com.project.ewalet.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    final String getAll = "SELECT * FROM USER";
    final String getById = "SELECT * FROM USER WHERE ID = #{id}";
    final String getByName = "SELECT * FROM USER WHERE NAME = #{name}";
    final String deleteById = "DELETE from USER WHERE ID = #{id}";
    final String insert = "INSERT INTO USER (NAME, BRANCH, PERCENTAGE, PHONE, EMAIL ) VALUES (#{name}, #{branch}, #{percentage}, #{phone}, #{email})";
    final String update = "UPDATE USER SET EMAIL = #{email}, NAME = #{name}, BRANCH = #{branch}, PERCENTAGE = #{percentage}, PHONE = #{phone} WHERE ID = #{id}";
    final String login = "UPDATE USER SET LOGIN_STATUS = TRUE WHERE ID = #{id}";
    final String logout = "UPDATE USER SET LOGIN_STATUS = FALSE WHERE ID = #{id}";
    final String auth = "SELECT * FROM USER WHERE EMAIL = #{email} AND PASSWORD = #{password}";

    final String findByEmail = "SELECT * FROM USER WHERE EMAIL = #{email}";
    final String save = "INSERT INTO USER (EMAIL, PASSWORD, FIRST_NAME, LAST_NAME, PHONE_NUMBER, TOKEN, STATUS, CREATED_AT ) " +
            "VALUES (#{email}, #{password}, #{first_name}, #{last_name}, #{phone_number}, #{token}, #{status}, #{created_at})";
    final String updateToken = "UPDATE USER SET TOKEN = #{token} WHERE ID = #{id}";

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
    User getById(int id);

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

