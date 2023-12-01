package com.example.birdinbackend.project;

import com.example.birdinbackend.member.Member;
import com.example.birdinbackend.member.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.Optional;

@RestController
public class ProjectController {
    final ProjectRepository repository;
    final MemberRepository memberRepository;

    public ProjectController(ProjectRepository repository, MemberRepository memberRepository) {
        this.repository = repository;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/projects")
    public Iterable<Project> getProjects(Principal principal) {
        System.out.println(principal.getName());
        return repository.findAll();
    }
    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id, Principal principal) {
        Optional<Project> response = repository.findByOwnerAndId(principal.getName(), id);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping(value = "/projects", consumes = {"application/json"})
    public ResponseEntity<Void> createProject(@RequestBody Project project, Principal principal, UriComponentsBuilder ucb) {
        project.setOwner(principal.getName());
        repository.save(project);
        memberRepository.save(new Member(principal.getName(), project));

        //return ResponseEntity.ok().build();
        URI locationOfNewCashCard = ucb
                .path("projects/{id}")
                .buildAndExpand(project.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
    }
}