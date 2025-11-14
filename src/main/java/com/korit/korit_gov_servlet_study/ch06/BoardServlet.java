package com.korit.korit_gov_servlet_study.ch06;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ch06/boards")
public class BoardServlet extends HttpServlet {
    private BoardRepository boardRepository;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        boardRepository = BoardRepository.getInstance();
        gson = new GsonBuilder().create();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Board jsonBoard = gson.fromJson(req.getReader(), Board.class);
        boardRepository.addBoard(jsonBoard);
        resp.setContentType("application/json");

        resp.setStatus(HttpServletResponse.SC_OK);
        SuccessResp successResponse = SuccessResp.builder()
                .message("게시물 등록 성공")
                .build();
        String boards = gson.toJson(successResponse);
        resp.getWriter().write(boards);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Board> boards = boardRepository.findAll();
        String bor = gson.toJson(boards);
        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(bor);
    }
}
