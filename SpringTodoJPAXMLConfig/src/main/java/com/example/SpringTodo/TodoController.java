package com.example.SpringTodo;

import com.example.SpringTodo.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TodoController {

    @Autowired
    private TodoService todoService;

    @RequestMapping("/")
    public ModelAndView index() {
        return new ModelAndView("todo_basic");
    }

    @PostMapping("/todo/upload")
    @ResponseBody
    public Map<String, String> upload(@RequestBody List<Todo> incomingTodos) {
        todoService.uploadTodos(incomingTodos);
        Map<String, String> response = new HashMap<>();
        response.put("status", "데이터 업로드 성공!");
        return response;
    }

    @GetMapping("/todo/download")
    @ResponseBody
    public List<Todo> download() {
        return todoService.getTodos();
    }

    @GetMapping("/todo/init")
    @ResponseBody
    public Map<String, String> init() {
        todoService.initTodos();
        Map<String, String> response = new HashMap<>();
        response.put("status", "초기화 성공!");
        return response;
    }
}
