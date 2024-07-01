package com.toyboard.demo.controller;

import com.toyboard.demo.dto.BoardDTO;
import com.toyboard.demo.entity.Board;
import com.toyboard.demo.service.BoardService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private static final Logger log = LoggerFactory.getLogger(BoardController.class);
    private final BoardService boardService;

    @GetMapping("/writeForm")
    public String writeForm() {
        return "/write";
    }

    @PostMapping("/write")
    public String writeBoard(@ModelAttribute BoardDTO boardDTO) throws IOException {
        boardService.saveBoard(boardDTO);
        return "redirect:/board";
    }

    @GetMapping()
    public String getBoardList(Model model) {
        model.addAttribute("boardList", boardService.getBoardList());
        return "/list";
    }

    @GetMapping("/{id}")
    public String getBoard(Model model, @PathVariable Long id) {
        BoardDTO boardDTO = boardService.getBoard(id);
        model.addAttribute("board", boardDTO);
        model.addAttribute("id", id);
        return "/detail";
    }

    @GetMapping("/updateForm/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.getBoard(id);
        model.addAttribute("boardUpdate", boardDTO);
        return "/update";
    }

    @PostMapping("/update")
    public String updateBoard(@ModelAttribute BoardDTO boardDTO) throws IOException {
        boardService.updateBoard(boardDTO);
//        model.addAttribute("board", board);
        return "redirect:/board";
    }

    @GetMapping("/delete/{id}")
    public String deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return "redirect:/board";
    }
}
