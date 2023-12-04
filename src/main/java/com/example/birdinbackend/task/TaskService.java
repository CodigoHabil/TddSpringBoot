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

    public Task getTask(Long id, Principal principal) {
        Optional<Task> task = repository.findById(id);

        if(task.isEmpty() || !isMemberOfProject(principal.getName(), task.get().getProject().getId())) {
            return task.orElse(null);
        }

        return task.get();
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

    public Task updateTask(Task task, Long idProject, Long id, Principal principal) {
        if(!isMemberOfProject(principal.getName(), idProject)) {
            return null;
        }

        Optional<Task> taskOptional = repository.findById(id);

        if(taskOptional.isEmpty()) {
            return null;
        }

        Task taskToUpdate = taskOptional.get();
        taskToUpdate.setTitle(task.getTitle());
        taskToUpdate.setDescription(task.getDescription());

        return repository.save(taskToUpdate);
    }

    private boolean isMemberOfProject(String username, Long idProject) {
        return memberRepository.findByUsernameAndId(username, idProject).isPresent();
    }

}
