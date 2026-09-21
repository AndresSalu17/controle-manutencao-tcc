package com.tcc.controledemanutencao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tcc.controledemanutencao.dto.UserRequestDTO;
import com.tcc.controledemanutencao.interfaces.UserServiceInterface;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceInterface userService;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/cadastro")
    public String registerForm(Model model) {

        model.addAttribute(
                "userRequestDTO",
                new UserRequestDTO());

        return "auth/register";
    }

    @PostMapping("/cadastro")
    public String register(
            @Valid @ModelAttribute UserRequestDTO dto,
            BindingResult result) {

        if (result.hasErrors()) {
            return "auth/register";
        }

        userService.save(dto);

        return "redirect:/auth/login?registered";
    }
}