package com.toyboard.demo.controller;

import com.toyboard.demo.dto.BoardDto;
import com.toyboard.demo.dto.BoardFileUploadDTO;
import com.toyboard.demo.dto.BoardResponseDTO;
import com.toyboard.demo.dto.BoardWriteRequestDTO;
import com.toyboard.demo.entity.Board;
import com.toyboard.demo.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/writeForm")
    public String writeForm() {
        return "/writeForm";
    }

    @PostMapping("/write")
    public String writeBoard(BoardWriteRequestDTO boardWriteRequestDTO, @ModelAttribute BoardFileUploadDTO boardFileUploadDTO) {
        boardService.saveBoard(boardWriteRequestDTO, boardFileUploadDTO);
        return "redirect:/board/list";
    }

    @GetMapping("/{id}")
    public String getBoard(Model model, @PathVariable Long id) {
        BoardResponseDTO result = boardService.getBoard(id);
        model.addAttribute("board", result);
        model.addAttribute("id", id);
        return "/boardDetail";
    }

    @GetMapping("/updateForm/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("board", boardService.getBoard(id));
        return "/updateForm";
    }

    @GetMapping("/list")
    public String getBoardList(Model model) {
        model.addAttribute("boardList", boardService.getBoardList());
        return "/boardList";
    }


    @PutMapping("/update")
    public String updateBoard(@ModelAttribute Board board) {
        System.out.println(board.getTitle());
        boardService.updateBoard(board);
        return "redirect:/board/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return "redirect:/board/list";
    }
}
