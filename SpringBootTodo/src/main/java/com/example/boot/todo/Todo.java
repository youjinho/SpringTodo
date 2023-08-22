package com.example.boot.todo;

import lombok.Data;

@Data
public class Todo {
    private String todo;
    private String date;
    private boolean done;
}
