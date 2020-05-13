package com.project.ewalet.mapper;

import com.project.ewalet.model.PaymentMethod;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface PaymentMethodMapper {

    final String getAll = "SELECT * FROM PAYMENT_METHOD";
    final String getById = "SELECT * FROM PAYMENT_METHOD WHERE ID = #{id}";
    final String findByCode = "SELECT * FROM payment_method where payment_type = #{payment_type}";

    @Select(getById)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "payment_type", column = "PAYMENT_TYPE"),
            @Result(property = "name", column = "NAME")
    })
    PaymentMethod getById(int id);

    @Select(getAll)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "payment_type", column = "PAYMENT_TYPE"),
            @Result(property = "name", column = "NAME")
    })
    ArrayList<PaymentMethod> getAll();

    @Select(findByCode)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "payment_type", column = "payment_type")
    })
    PaymentMethod findByCode(int payment_type);
}
