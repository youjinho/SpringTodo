package com.example.SpringTodo;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TodoMapper {
    
    @Select("SELECT * FROM todos")
    List<Todo> findAll();

    @Insert("INSERT INTO todos (todo, date, done) VALUES (#{todo}, #{date}, #{done})")
    void insert(Todo todo);

    @Delete("DELETE FROM todos")
    void deleteAll();
}