package com.example.SpringTodo;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TodoMapper {
    List<Todo> findAll();
    void insert(Todo todo);
    void deleteAll();
}