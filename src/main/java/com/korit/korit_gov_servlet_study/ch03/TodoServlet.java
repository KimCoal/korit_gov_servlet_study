package com.korit.korit_gov_servlet_study.ch03;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/ch03/todo")
public class TodoServlet extends HttpServlet {
    private TodoRepository todoRepository;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        todoRepository = TodoRepository.getInstance();
        gson = new GsonBuilder().create();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("application/json; charset=UTF-8");

        List<Todo> allTodo = todoRepository.getAllTodo();
        SuccessResponse successResponse = SuccessResponse.builder()
                .status(200)
                .message("전체 TODO 리스트 조회")
                .body(allTodo)
                .build();
        String todo = gson.toJson(successResponse);
        resp.getWriter().write(todo);


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("application/json; charset=UTF-8");

        TodoDto todoDto = gson.fromJson(req.getReader(), TodoDto.class);

        Todo foundTodo = todoRepository.findByTitle(todoDto.getTitle());

        if (foundTodo != null) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(400)
                    .message("중복")
                    .build();
            String error = gson.toJson(errorResponse);
            resp.getWriter().write(error);
            return;
        }

        Todo todo = todoRepository.addTodo(todoDto.toEntity());

        SuccessResponse successResponse = SuccessResponse.builder()
                .status(200)
                .message("TODO 등록 성공")
                .body(todo)
                .build();
        String str = gson.toJson(successResponse);
        resp.getWriter().write(str);

    }
}
