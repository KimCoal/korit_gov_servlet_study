package com.korit.korit_gov_servlet_study.ch02;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@WebServlet("/ch02/users")
public class UserServlet extends HttpServlet {
    private List<User> users;

    @Override
    public void init() throws ServletException {
        users = new ArrayList<>();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String email = req.getParameter("email");

        User user = User.builder()
                .username(username)
                .password(password)
                .name(name)
                .email(email)
                .build();

        validUser(user);

        users.add(user);
        System.out.println(users);

        // 응답
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("text/html; charset=UTF-8");
        resp.getWriter().println("사용자 등록 완료");

    }

    private Map<String, String> validUser(User user) {
        Map<String, String> error = new HashMap<>();

        Arrays.stream(user.getClass().getDeclaredFields()).forEach(f -> {
            f.setAccessible(true);
            String fieldname = f.getName();
            System.out.println(fieldname);
        });
        return error;
    }
}
