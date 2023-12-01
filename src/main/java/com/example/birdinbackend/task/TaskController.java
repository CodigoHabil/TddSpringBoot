package com.example.birdinbackend.task;

import com.example.birdinbackend.member.Member;
import com.example.birdinbackend.member.MemberRepository;
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
    private final MemberRepository memberRepository;

    public TaskController(TaskRepository repository, MemberRepository memberRepository) {
        this.repository = repository;
        this.memberRepository = memberRepository;
    }

    @PostMapping("/tasks")
    public ResponseEntity<Void> createTask(@RequestBody Task task, UriComponentsBuilder ucb, Principal principal ) {
        System.out.println(principal.getName());
        repository.save(task);
        URI locationOfNewCashCard = ucb
                .path("tasks/{id}")
                .buildAndExpand(1)
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id, Principal principal) {
        Optional<Task> response = repository.findById(id);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());    }
}
