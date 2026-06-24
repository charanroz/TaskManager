package com.charan.taskmanager.controller;

import com.charan.taskmanager.dto.AuthRequestDto;
import com.charan.taskmanager.dto.AuthResponseDto;
import com.charan.taskmanager.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponseDto register(@Valid @RequestBody AuthRequestDto dto) {
        return authService.register(dto);
    }

    @PostMapping("/login")
    public AuthResponseDto login(@Valid @RequestBody AuthRequestDto dto) {
        return authService.login(dto);
    }
}