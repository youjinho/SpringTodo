package com.example.SpringTodo;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.reactive.server.WebTestClient;

@ExtendWith(TestResultLogger.class)
@ExtendWith(MockitoExtension.class)
public class TodoControllerTest {

    private WebTestClient webTestClient;

    @Mock
    private TodoMapper todoMapper;

    @InjectMocks
    private TodoController todoController;

    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    public void setup() {
        // todoController에 todoService를 주입
        ReflectionTestUtils.setField(todoController, "todoService", todoService);

        // 오류 발생: 
        // WARN  org.springframework.context.annotation.AnnotationConfigApplicationContext 
        //   - Exception encountered during context initialization 
        //   - cancelling refresh attempt: org.springframework.beans.factory.UnsatisfiedDependencyException: 
        //     Error creating bean with name 'com.example.SpringTodo.TodoController': 
        //     Unsatisfied dependency expressed through field 'todoService'; 
        //   nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: 
        //   No qualifying bean of type 'com.example.SpringTodo.TodoService' available: 
        //   expected at least 1 bean which qualifies as autowire candidate. 
        //   Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}
        this.webTestClient = WebTestClient.bindToController(todoController).build();

        Todo todo1 = new Todo("Task1", "2023-08-14", false);
        Todo todo2 = new Todo("Task2", "2023-08-15", true);
        List<Todo> todos = Arrays.asList(todo1, todo2);

        // todoMapper.findAll() 호출을 흉내냄. 리턴 값을 todos로 함
        when(todoMapper.findAll()).thenReturn(todos);
    }

    @Test
    public void testDownloadTodos() {
        webTestClient.get().uri("/todo/download")
                     .exchange()
                     .expectStatus().isOk()
                     .expectBodyList(Todo.class)
                     .hasSize(2)
                     .consumeWith(response -> {
                         List<Todo> todos = response.getResponseBody();
                         assert todos != null;
                         assert "Task1".equals(todos.get(0).getTodo());
                         assert "Task2".equals(todos.get(1).getTodo());
                     });
    }
}
