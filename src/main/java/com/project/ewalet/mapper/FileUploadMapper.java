package com.project.ewalet.mapper;

import com.project.ewalet.model.FileUpload;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface FileUploadMapper {
    final String insert = "insert into file (id, user_id, file_type, path, file_name) values(#{id}, #{user_id}, #{file_type}, " +
            "#{path}, #{file_name})";
    final String findById = "SELECT * FROM file WHERE id = #{id}";
    final String findByPath = "SELECT * FROM file WHERE path = #{path}";

    @Insert(insert)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(FileUpload fileUpload);

    @Select(findById)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "file_type", column = "file_type"),
            @Result(property = "path", column = "path"),
            @Result(property = "file_name", column = "file_name")
    })
    FileUpload findById(int id);

    @Select(findByPath)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "user_id", column = "user_id"),
            @Result(property = "file_type", column = "file_type"),
            @Result(property = "path", column = "path"),
            @Result(property = "file_name", column = "file_name")
    })
    FileUpload findByPath(String path);
}
