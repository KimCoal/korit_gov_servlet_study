package com.korit.korit_gov_servlet_study.ch08.user.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.korit.korit_gov_servlet_study.ch08.user.dto.ApiRespDto;
import com.korit.korit_gov_servlet_study.ch08.user.dto.SignUpReqDto;
import com.korit.korit_gov_servlet_study.ch08.user.entity.User;
import com.korit.korit_gov_servlet_study.ch08.user.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/ch08/user")
public class UserServlet extends HttpServlet {

    private UserService userService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        userService = UserService.getInstance();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String keyword = req.getParameter("keyword");
        ApiRespDto<?> apiRespDto = null;
        if (username != null) {
            // username 조회
            Optional<User> foundUser = userService.findByUsername(username);
            if (foundUser.isPresent()) {
                apiRespDto = ApiRespDto.<User>builder()
                        .status("success")
                        .message(username + "회원 조회 완료")
                        .body(foundUser.get())
                        .build();
            }
        } else if (keyword != null) {
            // 키워드 조회
        } else {
            // 전체 조회
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        // 1. JSON → DTO 변환
        SignUpReqDto signUpReqDto = objectMapper.readValue(req.getReader(), SignUpReqDto.class);

        ApiRespDto<?> apiRespDto;

        // 2. username 중복 검사
        if (userService.isDuplicatedUsername(signUpReqDto.getUsername())) {
            apiRespDto = ApiRespDto.<String>builder()
                    .status("failed")
                    .message(signUpReqDto.getUsername() + "은 이미 사용중인 username입니다.")
                    .body(signUpReqDto.getUsername())
                    .build();

        } else {
            // 3. DB insert
            User savedUser = userService.addUser(signUpReqDto);

            apiRespDto = ApiRespDto.<User>builder()
                    .status("success")
                    .message("회원가입 완료")
                    .body(savedUser)
                    .build();
        }

        // 4. 응답 출력
        objectMapper.writeValue(resp.getWriter(), apiRespDto);
    }
}
