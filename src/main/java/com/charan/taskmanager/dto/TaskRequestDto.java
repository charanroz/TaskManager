package com.charan.taskmanager.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class TaskRequestDto {
    @NotBlank(message = "Title is required ")
    private String title;
    private String description;
    private LocalDate dueDate;
    private long categoryId;

}
