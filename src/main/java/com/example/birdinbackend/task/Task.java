package com.example.birdinbackend.task;

import com.example.birdinbackend.project.Project;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String status;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;


    public Task(String title, String description, String status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }
    public Task(String title, String description, String status, Project project) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Task task = (Task) o;
        return getId() != null && Objects.equals(getId(), task.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
