package com.project.ewalet.mapper;

import com.project.ewalet.model.BalanceCatalog;
import com.project.ewalet.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface BalanceCatalogMapper {

    final String getAll = "SELECT * FROM BALANCE_CATALOG";

    @Select(getAll)
    @Results(value = {
            @Result(property = "id", column = "ID"),
            @Result(property = "code", column = "CODE"),
            @Result(property = "BALANCE", column = "BALANCE")
    })
    ArrayList<BalanceCatalog> getAll();
}
