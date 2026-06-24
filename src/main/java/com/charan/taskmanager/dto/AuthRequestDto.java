package com.charan.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthRequestDto {

    private String username;

    @NotBlank
    private String password;
}
