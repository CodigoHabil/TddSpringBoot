package com.example.birdinbackend.task;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Optional;

@Controller
public class TaskController {
    private final TaskRepository repository;

    private final TaskService taskService;

    public TaskController(TaskRepository repository, TaskService taskService) {
        this.repository = repository;
        this.taskService = taskService;
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id, Principal principal) {
        Task task = taskService.getTask(id, principal);

        if(task == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(task);
    }

    @PostMapping("projects/{id}/tasks")
    public ResponseEntity<Void> createTaskByPost(@RequestBody Task task, @PathVariable Long id,  UriComponentsBuilder ucb, Principal principal) {
        Task newTask = taskService.createTask(task, id, principal);
        if(newTask == null) {
            return ResponseEntity.notFound().build();
        }

        URI locationOfNewCashCard = ucb
                .path("projects/{id}/tasks/{id_project}/")
                .buildAndExpand(1,1)
                .toUri();

        return ResponseEntity.created(locationOfNewCashCard).build();
    }

    @GetMapping("projects/{idProject}/tasks/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long idProject, @PathVariable Long id,  UriComponentsBuilder ucb, Principal principal) {
        Task task = taskService.getTask(idProject, id, principal.getName(), principal);

        if(task == null) {
            return ResponseEntity.notFound().build();
        }

        TaskDto taskDto = TaskMapper.INSTANCE.taskDto(task);

        return ResponseEntity.ok(taskDto);
    }

    @PutMapping("projects/{idProject}/tasks/{id}")
    public ResponseEntity<Void> updateTask(@PathVariable Long idProject, @PathVariable Long id, @RequestBody Task task,  UriComponentsBuilder ucb, Principal principal) {
        Task newTask = taskService.updateTask(task, idProject, id, principal);
        if(newTask == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().build();
    }
}