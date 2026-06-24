package com.charan.taskmanager.service;

import com.charan.taskmanager.dto.TaskRequestDto;
import com.charan.taskmanager.dto.TaskResponseDto;
import com.charan.taskmanager.entity.Category;
import com.charan.taskmanager.entity.Task;
import com.charan.taskmanager.entity.User;
import com.charan.taskmanager.exception.ResourceNotFoundException;
import com.charan.taskmanager.repository.CategoryRepository;
import com.charan.taskmanager.repository.TaskRepository;
import com.charan.taskmanager.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public TaskResponseDto createTask(TaskRequestDto dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setCompleted(false);
        task.setUser(getCurrentUser());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + dto.getCategoryId()));
            task.setCategory(category);
        }

        return mapToResponseDto(taskRepository.save(task));
    }

    public List<TaskResponseDto> getAllTasks() {
        User user = getCurrentUser();
        return taskRepository.findByUserId(user.getId())
                .stream().map(this::mapToResponseDto).collect(Collectors.toList());
    }

    public TaskResponseDto getTaskById(Long id) {
        Task task = findOwnedTask(id);
        return mapToResponseDto(task);
    }

    public TaskResponseDto updateTask(Long id, TaskRequestDto dto) {
        Task task = findOwnedTask(id);
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + dto.getCategoryId()));
            task.setCategory(category);
        }

        return mapToResponseDto(taskRepository.save(task));
    }

    public TaskResponseDto markAsCompleted(Long id) {
        Task task = findOwnedTask(id);
        task.setCompleted(true);
        return mapToResponseDto(taskRepository.save(task));
    }

    public void deleteTask(Long id) {
        Task task = findOwnedTask(id);
        taskRepository.delete(task);
    }

    public List<TaskResponseDto> searchByTitle(String keyword) {
        User user = getCurrentUser();
        return taskRepository.findByUserIdAndTitleContainingIgnoreCase(user.getId(), keyword)
                .stream().map(this::mapToResponseDto).collect(Collectors.toList());
    }

    private Task findOwnedTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + id));
        if (!task.getUser().getId().equals(getCurrentUser().getId())) {
            throw new ResourceNotFoundException("Task not found with id " + id);
        }
        return task;
    }

    private TaskResponseDto mapToResponseDto(Task task) {
        return TaskResponseDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.isCompleted())
                .dueDate(task.getDueDate())
                .categoryName(task.getCategory() != null ? task.getCategory().getName() : null)
                .build();
    }
}