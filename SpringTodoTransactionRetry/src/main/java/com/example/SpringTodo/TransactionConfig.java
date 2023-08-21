package com.example.SpringTodo;

import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

// 트랜잭션 Retry 활성화
//
// 1) @EnableRetry 어노테이션을 @Configuration 클래스에 추가
// 2) root-context.xml에 다음 줄을 추가하 
//    <bean class="org.springframework.retry.annotation.RetryConfiguration" />

@Configuration
//@EnableRetry    
public class TransactionConfig {

}
