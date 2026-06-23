package com.charan.taskmanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;
public class Task {

    @Entity
    @Table(name = "tasks")
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Task {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String title;

        private String description;

        @Column(nullable = false)
        private boolean completed = false;

        private LocalDate dueDate;

        @ManyToOne
        @JoinColumn(name = "category_id")
        private Category category;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private User user;
    }
}
