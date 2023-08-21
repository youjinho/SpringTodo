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
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TodoController {

    //@Autowired
    private TodoService todoService; // 이 서비스도 리액티브 타입을 반환하도록 수정해야 합니다.
    
    public TodoController(TodoService todoService) {
		this.todoService = todoService;
	}

    @RequestMapping("/")
    public String index() {
        return "todo_basic";
    }

    @PostMapping("/todo/upload")
    @ResponseBody
    public Mono<Map<String, String>> upload(@RequestBody List<Todo> incomingTodos) {
    	log.debug("/todo/upload");
    	
        return todoService.uploadTodos(incomingTodos) // 이 함수도 Mono나 Flux를 반환하도록 수정해야 합니다.
            .thenReturn(new HashMap<String, String>() {{
                put("status", "데이터 업로드 성공!");
            }});
    }

    @GetMapping("/todo/download")
    @ResponseBody
    public Mono<List<Todo>> download() {
    	log.debug("/todo/download");
    	
        return todoService.getTodos().collectList(); // 이 함수도 Mono나 Flux로 Todo를 반환하도록 수정해야 합니다.
    }

    @GetMapping("/todo/init")
    @ResponseBody
    public Mono<Map<String, String>> init() {
    	log.debug("/todo/init");
    	
        return todoService.initTodos() // 이 함수도 Mono나 Flux를 반환하도록 수정해야 합니다.
            .thenReturn(new HashMap<String, String>() {{
                put("status", "초기화 성공!");
            }});
    }
}
