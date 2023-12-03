package com.example.birdinbackend.task;

import com.example.birdinbackend.member.MemberRepository;
import com.example.birdinbackend.project.Project;
import com.example.birdinbackend.project.ProjectRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository repository;
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;


    public TaskService(TaskRepository repository, ProjectRepository projectRepository, MemberRepository memberRepository) {
        this.repository = repository;
        this.projectRepository = projectRepository;
        this.memberRepository = memberRepository;
    }

    public Task getTask(Long idProject, Long id, String username, Principal principal) {
        if(!isMemberOfProject(username, idProject)) {
            return null;
        }

        Optional<Task> task = repository.findById(id);
        return task.orElse(null);
    }

    public Task createTask(Task task, Long id, Principal principal) {
        if(!isMemberOfProject(principal.getName(), id)) {
            return null;
        }

        Optional<Project> project = projectRepository.findById(id);

        if(project.isEmpty()) {
            return null;
        }

        return repository.save(task);
    }

    private boolean isMemberOfProject(String username, Long idProject) {
        return memberRepository.findByUsernameAndId(username, idProject).isPresent();
    }

}
