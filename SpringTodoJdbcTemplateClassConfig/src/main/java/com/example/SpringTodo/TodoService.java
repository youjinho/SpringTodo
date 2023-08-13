package com.example.SpringTodo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TodoService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TodoService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Todo> getTodos() {
        String sql = "SELECT * FROM todos";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            String todo = resultSet.getString("todo");
            String date = resultSet.getString("date");
            boolean done = resultSet.getBoolean("done");
            return new Todo(todo, date, done);
        });
    }

    public void uploadTodos(List<Todo> todos) {
        String sql = "INSERT INTO todos (todo, date, done) VALUES (?, ?, ?)";
        for (Todo todo : todos) {
            jdbcTemplate.update(sql, todo.getTodo(), todo.getDate(), todo.isDone());
        }
    }

    public void initTodos() {
        String sql = "DELETE FROM todos";
        jdbcTemplate.update(sql);
    }
}
