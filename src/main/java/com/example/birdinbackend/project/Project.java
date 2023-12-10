package com.example.birdinbackend.project;

import com.example.birdinbackend.task.Task;
import com.example.birdinbackend.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name="user_project", joinColumns = @JoinColumn(name = "project_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName="id")
    )
    List<User> users = new ArrayList<>();

    public Project() {

    }

    public Long getId() {
        return id;
    }

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

    public void setId(Long id) {
        this.id = id;
    }

    public void getId(Long id) {
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<Task> getTask() {
        return task;
    }

    public void setTask(List<Task> task) {
        this.task = task;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Project project = (Project) o;
        return getId() != null && Objects.equals(getId(), project.getId());
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", owner='" + owner + '\'' +
                ", task=" + task +
                ", users=" + users +
                '}';
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}