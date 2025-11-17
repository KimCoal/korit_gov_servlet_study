package com.korit.korit_gov_servlet_study.ch07;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ch07/users")
public class UserServlet extends HttpServlet {

    private UserService userService;
    private SignUpReqDto signUpReqDto;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = UserService.getInstance();
        gson = new GsonBuilder().create();
        signUpReqDto = new SignUpReqDto();
    }

    // GET: username 있으면 단건, 없으면 전체
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String username = req.getParameter("username");
        resp.setContentType("application/json");

        if (username == null || username.isBlank()) {
            // 전체 조회
            List<User> users = userService.findAll();
            ResponseDto<List<User>> responseDto = ResponseDto.<List<User>>builder()
                    .status(200)
                    .message("전체 유저 조회 성공")
                    .body(users)
                    .build();
            String json = gson.toJson(responseDto);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(gson.toJson(json));
        } else {
            // 단건 조회
            User user = userService.findByUsername(username);

            ResponseDto responseDto = ResponseDto.builder()
                    .status(200)
                    .message(user == null ? "해당 username의 유저가 없습니다." : "단건 조회 성공")
                    .body(user)
                    .build();

            String json = gson.toJson(responseDto);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(gson.toJson(json));
        }
    }

    // POST: 회원 추가
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        SignUpReqDto signUpReqDto = gson.fromJson(req.getReader(), SignUpReqDto.class);

        resp.setContentType("application/json");

        // username 중복 확인
        if (userService.isDuplicatedUsername(signUpReqDto.getUsername())) {
            ResponseDto responseDto = ResponseDto.builder()
                    .status(400)
                    .message("username이 중복되었습니다.")
                    .body(null)
                    .build();

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(gson.toJson(responseDto));
            return;
        }

        // 중복 아니면 추가
        User savedUser = userService.signUp(signUpReqDto);

        ResponseDto<User> responseDto = ResponseDto.<User>builder()
                .status(200)
                .message("회원가입 성공")
                .body(savedUser)
                .build();

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(gson.toJson(responseDto));
    }
}
