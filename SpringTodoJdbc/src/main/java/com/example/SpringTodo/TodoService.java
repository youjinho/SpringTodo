package com.example.SpringTodo;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TodoService {

    private static final String DB_URL = "jdbc:mariadb://orion.mokpo.ac.kr:8300/tododb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "ScE1234**";
    private List<Map<String, Object>> todos = new ArrayList<>();

    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MariaDB Driver not found", e);
        }
    }
    
    public List<Map<String, Object>> getTodos() {
        return todos;
    }

    public void uploadTodos(List<Map<String, Object>> incomingTodos) {
        todos = incomingTodos;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {

            // Clear existing data
            statement.executeUpdate("DELETE FROM todos");

            // Insert new data
            for (Map<String, Object> todo : todos) {
                String todoText = (String) todo.get("todo");
                String date = (String) todo.get("date");
                boolean done = (Boolean) todo.get("done");
                statement.executeUpdate("INSERT INTO todos (todo, date, done) VALUES ('" + todoText + "', '" + date + "', " + done + ")");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void downloadTodos() {
        todos.clear();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM todos")) {

            while (resultSet.next()) {
                Map<String, Object> todo = new HashMap<>();
                todo.put("todo", resultSet.getString("todo"));
                todo.put("date", resultSet.getString("date"));
                todo.put("done", resultSet.getBoolean("done"));
                todos.add(todo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initTodos() {
        todos.clear();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DELETE FROM todos");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
