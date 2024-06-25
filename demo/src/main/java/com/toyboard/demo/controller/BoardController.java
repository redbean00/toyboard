package com.toyboard.demo.controller;

import com.toyboard.demo.entity.Board;
import com.toyboard.demo.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public String getBoardList(Model model) {
        model.addAttribute("boardList", boardService.getBoardList());
        return "/boardList";

    }

    @PostMapping
    public String createBoard(Board board) {
        boardService.createBoard(board);
        return "redirect:/";
    }

    @PutMapping
    public String updateBoard(Board board) {
        boardService.updateBoard(board);
        return "redirect:/";
    }

    @DeleteMapping
    public String deleteBoard(Board board) {
        boardService.deleteBoard(board);
        return "redirect:/";
    }


}
