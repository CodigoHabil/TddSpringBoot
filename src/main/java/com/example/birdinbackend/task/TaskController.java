package com.example.birdinbackend.task;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Optional;

@Controller
public class TaskController {
    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/task")
    public ResponseEntity<Void> createTask(@RequestBody Task task, UriComponentsBuilder ucb ) {
        repository.save(task);
        URI locationOfNewCashCard = ucb
                .path("task/{id}")
                .buildAndExpand(1)
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }
    @GetMapping("/task/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id, Principal principal) {
        Optional<Task> response = repository.findById(id);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
