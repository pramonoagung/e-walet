package com.project.ewalet.mapper;

import com.project.ewalet.model.PaymentMethod;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface PaymentMethodMapper {

    final String getAll = "SELECT * FROM PAYMENT_METHOD";

    @Select(getAll)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "payment_type", column = "PAYMENT_TYPE"),
            @Result(property = "name", column = "NAME")
    })
    ArrayList<PaymentMethod> getAll();
}
