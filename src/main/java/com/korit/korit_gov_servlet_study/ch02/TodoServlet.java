package com.korit.korit_gov_servlet_study.ch02;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@WebServlet("/ch02/todo")
public class TodoServlet extends HttpServlet {

    /*
    * List에 todo를 저장
    * 저장요청시 쿼리파라미터에서 값을 가져와 리스트에 저장
    * 저장 전에 3가지 필드가 다 채워져있는지 확인
    * 빈값있으면 map에 필드명과 빈값 일 수 없습니다 넣고 응답 400
    * 조회요청시 쿼리파라미터가 없으면 전체 조회
    * 있으면 title로 단건 조회
    * 해당 title 투두가 없으면 해당 todo가 없습니다, 응답 404
    * */

    private List<Todo> todoList;


    @Override
    public void init() throws ServletException {
        todoList = new ArrayList<>();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("text/html; charset=UTF-8");
        String title = req.getParameter("title");

        List<Todo> foundList = todoList.stream()
                .filter(f -> f.getTitle().equals(title))
                .toList();

        if (title == null || title.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(todoList);
        } else if (foundList.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().println("해당 TODO가 없습니다");
            return;
        } else {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println(foundList);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("text/html; charset=UTF-8");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String username = req.getParameter("username");

        Todo todo = Todo.builder()
                .title(title)
                .content(content)
                .username(username)
                .build();

        Map<String, String> error = validTodo(todo);

        if (!error.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println(error);
            return;
        }

        todoList.add(todo);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println("TODO 등록 완료");



    }

    private Map<String, String> validTodo(Todo todo) {
        Map<String, String> error = new HashMap<>();

        // user 객체의 선언된 모든 필드를 stream으로 순회
        Arrays.stream(todo.getClass().getDeclaredFields()).forEach(f -> {
            // private 필드에도 접근할 수 있게 강제로 접근 허용
            f.setAccessible(true);
            String fieldName = f.getName();
            System.out.println(fieldName);


            try {
                // 리플렉션으로 user 인스턴스의 해당 필드값 꺼내기
                Object fieldValue = f.get(todo);
                System.out.println(fieldValue);

                // 만약 해당 필드값이 null이면 검증 실패로 간주
                if (fieldValue == null) {
                    throw new RuntimeException();
                }

                // 필드값이 문자열일 때 공백/빈문자열이면 실패로 간주
                if (fieldValue.toString().isBlank()) {
                    throw new RuntimeException();
                }
            } catch (IllegalAccessException e) {
                // 필드 접근 권한 문제(드물게 발생)
                System.out.println("필드에 접근할 수 없습니다.");
            } catch (RuntimeException e) {
                // 위에서 던진 예외를 여기서 받아서 해당 필드에 대한 에러메시지 추가
                error.put(fieldName, "빈 값일 수 없습니다.");
            }

        });
        return error;
    }
}
