package com.example.birdinbackend.member;

import com.example.birdinbackend.project.Project;
import com.example.birdinbackend.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User user;
    private String username;

    @ManyToOne
    private Project project;
    private Boolean enabled = true;

    public Member() {
    }

    public Member( User user, String username, Project project) {
        this.user = user;
        this.username = username;
        this.project = project;
    }

    public Member(  String username, Project project) {
        this.username = username;
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Member member = (Member) o;
        return getId() != null && Objects.equals(getId(), member.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
