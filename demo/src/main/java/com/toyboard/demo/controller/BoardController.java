package com.toyboard.demo.controller;

import com.toyboard.demo.entity.Board;
import com.toyboard.demo.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/")
    public String getBoardList(Model model) {
        model.addAttribute("boardList", boardService.getBoardList());
        return "/boardList";
    }

    @GetMapping("/updateForm")
    public String updateForm() {
        return "/updateForm";
    }

    @GetMapping("/{id}")
    public String getBoard(Model model, @PathVariable Long id) {
        model.addAttribute("board", boardService.getBoard(id));
        return "/boardDetail";
    }

    @PostMapping
    public String createBoard(Board board) {
        boardService.createBoard(board);
        return "redirect:/";
    }

    @PutMapping("/{id}")
    public String updateBoard(@PathVariable Long id) {
        boardService.updateBoard(id);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return "redirect:/";
    }


}
