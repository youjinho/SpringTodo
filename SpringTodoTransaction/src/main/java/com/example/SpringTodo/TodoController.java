package com.example.SpringTodo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    public Map<String, String> upload(@RequestBody List<Todo> incomingTodos) throws Exception {
    	log.debug("/todo/upload");
    	
        todoService.uploadTodos(incomingTodos);
        Map<String, String> response = new HashMap<>();
        response.put("status", "데이터 업로드 성공!");
        return response;
    }

    @GetMapping("/todo/download")
    @ResponseBody
    public List<Todo> download() {
    	log.debug("/todo/download");
    	
        return todoService.getTodos();
    }

    @GetMapping("/todo/init")
    @ResponseBody
    public Map<String, String> init() throws Exception {
    	log.debug("/todo/init");
    	
        todoService.initTodos();
        Map<String, String> response = new HashMap<>();
        response.put("status", "초기화 성공!");
        return response;
    }
}
