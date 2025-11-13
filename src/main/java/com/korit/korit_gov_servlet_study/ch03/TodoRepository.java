package com.korit.korit_gov_servlet_study.ch03;

import java.util.ArrayList;
import java.util.List;

public class TodoRepository {
    private List<Todo> todos;
    private Integer todoId = 1;
    private static TodoRepository instance;

    private TodoRepository() {
        todos = new ArrayList<>();
    }

    public static TodoRepository getInstance() {
        if (instance == null) {
            instance = new TodoRepository();
        }
        return instance;
    }

    public Todo findByTitle(String title) {
        return todos.stream().filter(t -> t.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }

    public Todo addTodo(Todo todo) {
        todo.setTodoId(todoId++);
        todos.add(todo);
        return todo;
    }

    public List<Todo> getAllTodo() {
        return todos;
    }
}
