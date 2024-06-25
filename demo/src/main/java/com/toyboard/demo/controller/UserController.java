package com.toyboard.demo.controller;

import com.toyboard.demo.dto.UserDto;
import com.toyboard.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/loginForm";
    }

    @GetMapping("/signUpForm")
    public String signUpForm() {
        return "/signUpForm";
    }

    @PostMapping("/login")
    public String loginUp(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
        return "redirect:/";
    }

    @PostMapping("/signUp")
    public String signUp(@RequestBody UserDto userDto) {
        userService.getUser(userDto);
        return "redirect:/";
    }

}
