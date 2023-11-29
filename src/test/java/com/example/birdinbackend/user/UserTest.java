package com.example.birdinbackend.user;

import com.example.birdinbackend.project.Project;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class UserTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void createNewUser(){
        User user = new User("sarah1", "abc123", "sarah@email.com");
        ResponseEntity<Void> createResponse = restTemplate
                .postForEntity("/users", user, Void.class);
        Assertions.assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void zshouldReturnAUserWhenDataIsSaved() {
        User user = new User("sarah1", "abc123", "sarah@email.com");
        restTemplate
                .postForEntity("/users", user, Void.class);
        ResponseEntity<String> response = restTemplate
                .getForEntity("/users/1", String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        System.out.println(response.getBody());
    }

    @Test
    void createProjectAndUser(){
        Project project = new Project(1L,"BirdIn Software", "This is a project", "sarah1");
        ResponseEntity<Void> createResponse = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .postForEntity("/project", project, Void.class);
        Assertions.assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        List<Project> projectList = new ArrayList<Project>();

        projectList.add(project);


        User user = new User("sarah1", "abc123", "sarah@email.com");
        user.setProjects(projectList);

        restTemplate
                .postForEntity("/users", user, Void.class);
        ResponseEntity<String> response = restTemplate
                .getForEntity("/users/1", String.class);

        System.out.println(response.getBody());

    }
}