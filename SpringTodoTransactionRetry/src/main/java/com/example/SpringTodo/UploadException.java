package com.example.SpringTodo;

//기본 예외 클래스 임포트
import java.lang.Exception;

public class UploadException extends RuntimeException {

 public UploadException() {
     super();
 }

 public UploadException(String message) {
     super(message);
 }

 public UploadException(String message, Throwable cause) {
     super(message, cause);
 }

 public UploadException(Throwable cause) {
     super(cause);
 }
}


