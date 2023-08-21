package com.example.SpringTodo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
public class TodoService {

    private final TodoMapper todoMapper;

    @Autowired
    public TodoService(TodoMapper todoMapper) {
        this.todoMapper = todoMapper;
    }

    public Flux<Todo> getTodos() {
    	// todoMapper.findAll()가 동기작업이어서
    	// 별도 쓰레드를 통해 작업을 처리하도록 하여
    	// getTodos() 메소드는 대기하지 않고 바로 리턴하도록 함
        return Flux.defer(() -> Flux.fromIterable(todoMapper.findAll()))
                   .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Void> uploadTodos(List<Todo> todos) {
        return Mono.defer(() -> {
            todoMapper.deleteAll(); // 기존 데이터를 삭제하고 새로운 데이터를 업로드
            for (Todo todo : todos) {
                todoMapper.insert(todo);
            }
            return Mono.empty().then();
        }).subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<Void> initTodos() {
        return Mono.defer(() -> {
            todoMapper.deleteAll(); // 모든 데이터 삭제
            return Mono.empty().then();
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
