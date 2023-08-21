package com.example.SpringTodo;

//기본 예외 클래스 임포트
import java.lang.Exception;

public class InitException extends Exception {

 public InitException() {
     super();
 }

 public InitException(String message) {
     super(message);
 }

 public InitException(String message, Throwable cause) {
     super(message, cause);
 }

 public InitException(Throwable cause) {
     super(cause);
 }
}
