package com.project.ewalet.mapper;

import com.project.ewalet.model.BalanceCatalog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
@Mapper
public interface BalanceCatalogMapper {

    final String getAll = "select * from balance_catalog";
    final String findByCode = "select * from balance_catalog where code = #{code}";

    @Select(getAll)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "code", column = "code"),
            @Result(property = "balance", column = "balance")
    })
    ArrayList<BalanceCatalog> getAll();

    @Select(findByCode)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "code", column = "code"),
            @Result(property = "balance", column = "balance")
    })
    BalanceCatalog findByCode(String code);
}
