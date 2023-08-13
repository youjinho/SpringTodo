package com.example.todo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/todo/*")
public class TodoController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mariadb://orion.mokpo.ac.kr:8300/tododb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "ScE1234**";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();

        try {
            if ("/download".equals(action)) {
                download(request, response);
            } else if ("/init".equals(action)) {
                init(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error processing request");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();

        try {
            if ("/upload".equals(action)) {
                upload(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error processing request");
        }
    }

    private Connection connect() throws Exception {
        Class.forName("org.mariadb.jdbc.Driver");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONArray todos = new JSONArray();
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM todos");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                JSONObject todo = new JSONObject();
                todo.put("todo", rs.getString("todo"));
                todo.put("date", rs.getString("date"));
                todo.put("done", rs.getBoolean("done"));
                todos.put(todo);
            }
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String todosStr = todos.toString();
        response.getWriter().write(todos.toString());
    }

    private void upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONArray todos = new JSONArray(request.getReader().readLine());

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM todos")) {
             stmt.executeUpdate();
        }
        
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO todos (todo, date, done) VALUES (?, ?, ?)")) {
            conn.setAutoCommit(false);
            for (int i = 0; i < todos.length(); i++) {
                JSONObject todo = todos.getJSONObject(i);
                stmt.setString(1, todo.getString("todo"));
                stmt.setString(2, todo.getString("date"));
                stmt.setBoolean(3, todo.getBoolean("done"));
                stmt.executeUpdate();
            }
            conn.commit();
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new JSONObject().put("status", "데이터 업로드 성공!").toString());
    }

    private void init(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM todos")) {
            stmt.executeUpdate();
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new JSONObject().put("status", "초기화 성공!").toString());
    }
}
