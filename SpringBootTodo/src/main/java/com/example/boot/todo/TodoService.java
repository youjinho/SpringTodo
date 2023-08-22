package com.example.boot.todo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoMapper todoMapper;

    public TodoService(TodoMapper todoMapper) {
        this.todoMapper = todoMapper;
    }

    public List<Todo> getTodos() {
        return todoMapper.findAll();
    }

    public void uploadTodos(List<Todo> todos) {
        todoMapper.deleteAll(); // 기존 데이터를 삭제하고 새로운 데이터를 업로드
        for (Todo todo : todos) {
            todoMapper.insert(todo);
        }
    }

    public void initTodos() {
        todoMapper.deleteAll(); // 모든 데이터 삭제
    }
}
