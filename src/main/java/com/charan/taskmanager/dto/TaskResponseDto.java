package com.charan.taskmanager.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class TaskResponseDto {

    private long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDate dueDate;
    private String categoryName;
}
