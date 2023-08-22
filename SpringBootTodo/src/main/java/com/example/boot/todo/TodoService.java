package com.example.boot.todo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TodoService {

    private final TodoMapper todoMapper;

    public TodoService(TodoMapper todoMapper) {
        this.todoMapper = todoMapper;
    }

    public List<Todo> getTodos() {
        return todoMapper.findAll();
    }

    @Transactional // RuntimeException이 발생하면 트랙잰션이 롤백된다.
    public void uploadTodos(List<Todo> todos) {
        todoMapper.deleteAll(); // 기존 데이터를 삭제하고 새로운 데이터를 업로드
        
        // 실험적으로 0.7의 확률로 무작위 오류 발생
        // RuntimeException을 받으면 트랜잭션이 Rollback 된다.
        if (Math.random() <= -0.7) {  // 0.7 정도로 오류 발생 확률을 높여서 실험
            throw new RuntimeException("uploadTodos DB 작업 오류 발생");
        }
        
        for (Todo todo : todos) {
            todoMapper.insert(todo);
        }
    }

    public void initTodos() {
        todoMapper.deleteAll(); // 모든 데이터 삭제
    }
}
