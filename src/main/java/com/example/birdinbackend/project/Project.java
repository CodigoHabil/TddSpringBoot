package com.example.birdinbackend.project;

import com.example.birdinbackend.task.Task;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Project {
    @Id
    @GeneratedValue
    Long id;
    String title;
    String description;
    String owner;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    List<Task> task = new ArrayList<>();

    public Project(long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Project( String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Project(long id, String title, String description, String owner) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Project project = (Project) o;
        return getId() != null && Objects.equals(getId(), project.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}