package com.example.birdinbackend.project;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    Optional<Project> findByOwnerAndId(String name, Long id);
}

