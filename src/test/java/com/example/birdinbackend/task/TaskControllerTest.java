package com.example.birdinbackend.task;

import com.example.birdinbackend.project.Project;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class TaskControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void itCantCreateATaskWithAValidProject() {
        Project project = new Project(1L,"BirdIn Software", "This is a project", "sarah1");
        restTemplate
                .withBasicAuth("sarah1", "abc123")
                .postForEntity("/projects", project, Void.class);
        Task task = new Task("BirdIn Software", "This is a task", "sarah1");
        task.setProject(project);

        ResponseEntity<Void> createResponse = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .postForEntity("/projects/1/tasks", task, Void.class);
        URI locationOfTask = createResponse.getHeaders().getLocation();

        System.out.println(createResponse.getStatusCode());

        Assertions.assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ResponseEntity<String> responseGetTask = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .getForEntity("/projects/1/tasks/1", String.class);

        System.out.println(responseGetTask.getBody());
        Number id = JsonPath.parse(responseGetTask.getBody()).read("$.id");
        Assertions.assertThat(id).isNotNull();
        Assertions.assertThat(responseGetTask.getStatusCode()).isEqualTo(HttpStatus.OK);


    }

    @Test
    void itUpdatesTasks() {
        Project project = new Project(1L,"BirdIn Software", "This is a project", "sarah1");
        restTemplate
                .withBasicAuth("sarah1", "abc123")
                .postForEntity("/projects", project, Void.class);
        Task task = new Task("BirdIn Software", "This is a task", "sarah1");
        task.setProject(project);

        ResponseEntity<Void> createResponse = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .postForEntity("/projects/1/tasks", task, Void.class);
        URI locationOfTask = createResponse.getHeaders().getLocation();

        System.out.println(createResponse.getStatusCode());

        Assertions.assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ResponseEntity<String> responseGetTask = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .getForEntity("/projects/1/tasks/1", String.class);

        System.out.println(responseGetTask.getBody());
        Number id = JsonPath.parse(responseGetTask.getBody()).read("$.id");
        Assertions.assertThat(id).isNotNull();
        Assertions.assertThat(responseGetTask.getStatusCode()).isEqualTo(HttpStatus.OK);

        Task taskUpdated = new Task("Its not birdin", "This is a task", "sarah1");
        taskUpdated.setProject(project);
        taskUpdated.setId(1L);

        HttpEntity<Task> request = new HttpEntity<>(taskUpdated);

        ResponseEntity<Void> updateResponse = restTemplate
                .withBasicAuth("sarah1", "abc123")
                        .exchange("/projects/1/tasks/1", HttpMethod.PUT, request, Void.class);
        Assertions.assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<String> responseUpdatedTask = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .getForEntity("/projects/1/tasks/1", String.class);
        System.out.println(responseUpdatedTask.getBody());
        Assertions.assertThat(responseUpdatedTask.getBody()).contains("Its not birdin");
    }

    void itReturnsATaskBasedOnTheProject() {
    }

    void itCantReturnATaskBasedOnTheProject() {

    }


    }