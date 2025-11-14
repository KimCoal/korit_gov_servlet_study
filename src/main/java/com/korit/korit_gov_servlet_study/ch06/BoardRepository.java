package com.korit.korit_gov_servlet_study.ch06;

import java.util.ArrayList;
import java.util.List;

public class BoardRepository {
    private static BoardRepository instance;
    private List<Board> boards;

    private BoardRepository () {
        boards = new ArrayList<>();
    }

    public static BoardRepository getInstance() {
        if (instance == null) {
            instance = new BoardRepository();
        }
        return instance;
    }

    public List<Board> findAll() {
        return boards;
    }

    public Board addBoard(Board board){
        boards.add(board);
        return board;
    }
}
