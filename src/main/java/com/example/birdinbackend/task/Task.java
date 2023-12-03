package com.example.birdinbackend.task;

import com.example.birdinbackend.project.Project;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.context.annotation.Lazy;

import java.util.Objects;


@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String status;
    @ManyToOne()
    @JoinColumn(name = "project_id")
    private Project project;

    public Task() {
    }
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
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
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", project=" + project +
                '}';
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
