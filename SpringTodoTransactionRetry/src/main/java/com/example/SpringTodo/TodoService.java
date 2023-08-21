package com.example.SpringTodo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TodoService {

    private final TodoMapper todoMapper;

    @Autowired
    public TodoService(TodoMapper todoMapper) {
        this.todoMapper = todoMapper;
    }

    public List<Todo> getTodos() {
        return todoMapper.findAll();
    }

    @Retryable(value = UploadException.class, maxAttempts = 2, backoff = @Backoff(delay = 3000))
    @Transactional
    public void uploadTodos(List<Todo> todos) throws UploadException {
    	try {
    		log.debug("uploadTodos 시작");
    		
	        todoMapper.deleteAll(); // 기존 데이터를 삭제하고 새로운 데이터를 업로드
	        
	        // 실험적으로 0.7의 확률로 무작위 오류 발생
	        if (Math.random() <= 0.7) {
	            throw new Exception("uploadTodos DB 작업 오류 발생");
	        }
	        
	        for (Todo todo : todos) {
	            todoMapper.insert(todo);
	        }
    	} catch (Exception e) {
            throw new UploadException("업로드 중 오류 발생", e);
        }
    }

    @Retryable(value = InitException.class, maxAttempts = 2, backoff = @Backoff(delay = 3000))    
    @Transactional
    public void initTodos() throws InitException {
    	try {
    		todoMapper.deleteAll(); // 모든 데이터 삭제
    		
	        // 실험적으로 0.7의 확률로 무작위 오류 발생
	        if (Math.random() <= 0.7) {
	            throw new Exception("initTodos DB 작업 오류 발생");
	        }
	        
    	} catch (Exception e) {
            throw new InitException("초기화 중 오류 발생", e);
        }        
    }
    
    @Recover
    public void recover(Exception e, List<Todo> todos) {
        // uploadTodos 메소드에 대한 복구 로직
    	log.info("recover(): 복구 작업: " + e.getMessage());
    }

    @Recover
    public void recoverUpload(UploadException e, List<Todo> todos) throws UploadException {
        // uploadTodos 메소드에 대한 복구 로직
    	log.info("uploadTodos 복구 작업");
    	throw new UploadException("uploadTodos 작업 복구 실패" );
    }

    @Recover
    public void recoverInit(InitException e)  throws InitException {
        // initTodos 메소드에 대한 복구 로직
    	log.info("initTodos 복구 작업");
    	throw new InitException("initTodos 작업 복구 실패" );
    }
    
}