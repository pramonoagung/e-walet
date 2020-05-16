package com.project.ewalet.mapper;

import com.project.ewalet.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    final String getAll = "select * from user";
    final String getById = "select * from user where id = #{id}";
    final String getByName = "select * from user where name = #{name}";
    final String deleteById = "delete from user where id = #{id}";

    final String update = "update user set email = #{email}, password = #{password}, first_name = #{first_name}, " +
            "last_name = #{last_name}, status = #{status}, phone_number = #{phone_number} where id = #{id}";

    final String login = "update user set login_status = true where id = #{id}";
    final String logout ="update user set login_status = false where id = #{id}";
    final String auth = "select * from user where email = #{email} and password = #{password}";

    final String findByPhoneNumber = "select * from user where phone_number = #{phone_number}";
    final String findByEmail = "select * from user where email = #{email}";
    final String findByEmailAndPhone = "select * from user where email = #{email} and phone_number = #{phone_number}";
    final String save = "insert into user (email, password, first_name, last_name, phone_number, status, created_at ) " +
            "values (#{email}, #{password}, #{first_name}, #{last_name}, #{phone_number}, #{status}, #{created_at})";
    final String updateToken = "update user set token = #{token} where id = #{id}";

    @Select(findByPhoneNumber)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "first_name", column = "first_name"),
            @Result(property = "last_name", column = "last_name"),
            @Result(property = "phone_number", column = "phone_number"),
            @Result(property = "status", column = "status"),
            @Result(property = "created_at", column = "created_at")
    })
    User findByPhoneNumber(String phone_number);

    @Select(findByEmail)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "first_name", column = "first_name"),
            @Result(property = "last_name", column = "last_name"),
            @Result(property = "phone_number", column = "phone_number"),
            @Result(property = "token", column = "TOKEN"),
            @Result(property = "status", column = "status"),
            @Result(property = "created_at", column = "created_at")
    })
    User findByEmail(String email);

    @Select(findByEmailAndPhone)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "first_name", column = "first_name"),
            @Result(property = "last_name", column = "last_name"),
            @Result(property = "phone_number", column = "phone_number"),
            @Result(property = "token", column = "token"),
            @Result(property = "status", column = "status"),
            @Result(property = "created_at", column = "created_at")
    })
    User findByEmailAndPhone(String email, String phone_number);

    @Insert(save)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(User user);

    @Update(updateToken)
    void updateToken(String token, long id);

    @Select(getAll)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "name", column = "name"),
            @Result(property = "loginStatus", column = "login_status")
    })
    List<User> getAll();

    @Select(getById)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "name", column = "name"),
            @Result(property = "loginStatus", column = "login_status")
    })
    User getById(long id);

    @Select(getByName)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "name", column = "name"),
            @Result(property = "loginStatus", column = "login_status")
    })
    User getByName(String name);

    @Select(auth)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "email", column = "email"),
            @Result(property = "password", column = "password"),
            @Result(property = "name", column = "name"),
            @Result(property = "loginStatus", column = "login_status")
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

}

