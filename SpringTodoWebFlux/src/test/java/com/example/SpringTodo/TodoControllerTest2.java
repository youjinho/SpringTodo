package com.example.SpringTodo;

import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;

@ExtendWith(TestResultLogger.class)
@ExtendWith(MockitoExtension.class)
public class TodoControllerTest2{

    private WebTestClient testClient;

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoController todoController;

    @BeforeEach
    public void setup() {
        testClient = WebTestClient.bindToController(todoController).build();
    }

    @Test
    public void testGetTodo() {
        Todo mockTodo = new Todo("Sample Task", "2023-08-22", false);
        List<Todo> mockTodos = Arrays.asList(mockTodo);
        when(todoService.getTodos()).thenReturn(Flux.just(mockTodo));

        testClient.get().uri("/todo/download")
            .exchange()
            .expectStatus().isOk()
            // .expectBody(List<Todo>.class).isEqualTo(mockTodos); // 문법오류: 제너릭 표현 허용 안됨
        	.expectBody(new ParameterizedTypeReference<List<Todo>>() {});
    }
}
