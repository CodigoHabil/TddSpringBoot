package com.example.birdinbackend.project;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Optional;

@RestController
public class ProjectController {
    final ProjectRepository repository;

    public ProjectController(ProjectRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/project")
    public Iterable<Project> getProjects(Principal principal) {
        System.out.println(principal.getName());
        return repository.findAll();
    }
    @GetMapping("/project/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id, Principal principal) {
        System.out.println(principal.getName());
        Optional<Project> response = repository.findByOwnerAndId(principal.getName(), id);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping(value = "/project", consumes = {"application/json"})
    public ResponseEntity<Void> createProject(@RequestBody Project project, Principal principal, UriComponentsBuilder ucb) {
        project.setOwner(principal.getName());
        repository.save(project);

        //return ResponseEntity.ok().build();
        URI locationOfNewCashCard = ucb
                .path("project/{id}")
                .buildAndExpand(project.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }
}
