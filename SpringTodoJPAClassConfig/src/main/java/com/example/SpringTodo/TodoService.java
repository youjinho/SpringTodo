package com.example.SpringTodo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TodoService {
    
    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> getTodos() {
        return todoRepository.findAll();
    }

    public void uploadTodos(List<Todo> incomingTodos) {
        todoRepository.deleteAll();
        todoRepository.saveAll(incomingTodos);
    }

    public void initTodos() {
        todoRepository.deleteAll();
    }
}