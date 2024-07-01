package com.toyboard.demo.controller;

import com.toyboard.demo.dto.UserDTO;
import com.toyboard.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/signupForm")
    public String signupForm() {
        return "/signup";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/login";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute UserDTO userDTO) {
        userService.save(userDTO);
        return "redirect:/board";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserDTO userDTO) {
        UserDetails userDetails = userService.loadUserByUsername(userDTO.getUsername());

        return "redirect:/board";
    }

}
