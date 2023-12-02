package com.example.birdinbackend.member;

import com.example.birdinbackend.project.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long> {
    public Optional<Member> findByProjectAndUsername(Project project, String username);

    Optional<Member> findByUsernameAndId(String name, Long id);
}
