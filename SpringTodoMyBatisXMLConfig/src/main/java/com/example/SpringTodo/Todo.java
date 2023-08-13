package com.example.SpringTodo;

public class Todo {
    private String todo;
    private String date;
    private boolean done;

    public Todo() {
    }

    public Todo(String todo, String date, boolean done) {
        this.todo = todo;
        this.date = date;
        this.done = done;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "todo='" + todo + '\'' +
                ", date='" + date + '\'' +
                ", done=" + done +
                '}';
    }
}

