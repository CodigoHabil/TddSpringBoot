package com.example.birdinbackend.task;

import com.example.birdinbackend.member.MemberRepository;
import com.example.birdinbackend.project.Project;
import com.example.birdinbackend.project.ProjectRepository;
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
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

    private final TaskService taskService;

    public TaskController(TaskRepository repository, ProjectRepository projectRepository, MemberRepository memberRepository, TaskService taskService) {
        this.repository = repository;
        this.projectRepository = projectRepository;
        this.memberRepository = memberRepository;
        this.taskService = taskService;
    }

    @PostMapping("/tasks")
    public ResponseEntity<Void> createTask(@RequestBody Task task, UriComponentsBuilder ucb, Principal principal) {
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
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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

        /*
        if(memberRepository.findByUsernameAndId(principal.getName(), id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Project> project = projectRepository.findById(id);

        if(project.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        task.setProject(project.get());

        repository.save(task);


        URI locationOfNewCashCard = ucb
                .path("projects/{id}/tasks/{id_project}/")
                .buildAndExpand(1,1)
                .toUri();
        return ResponseEntity.created(locationOfNewCashCard).build();
        */

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
}