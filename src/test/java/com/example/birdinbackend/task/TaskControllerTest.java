package com.example.birdinbackend.task;

import com.example.birdinbackend.project.Project;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class TaskControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldCreateANewTask() {
        Project project = new Project(1L,"BirdIn Software", "This is a project", "sarah1");
        restTemplate
                .withBasicAuth("sarah1", "abc123")
                .postForEntity("/projects", project, Void.class);

        Task task = new Task("BirdIn Software", "This is a task", "sarah1", project);
        ResponseEntity<Void> createResponse = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .postForEntity("/tasks", task, Void.class);

        URI locationOfTask = createResponse.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .getForEntity(locationOfTask, String.class);
        Assertions.assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }

    @Test
    void zshouldReturnATask() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .getForEntity("/tasks/1", String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        System.out.println(response.getBody());

        Number id = documentContext.read("$.id");
        Assertions.assertThat(id).isNotNull();

    }

    @Test
    void itCreatesATaskBasedOnAProject() {
        ResponseEntity<String> response = restTemplate
                .withBasicAuth("sarah1", "abc123")
                .getForEntity("/tasks/1", String.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        System.out.println(response.getBody());

        Number id = documentContext.read("$.id");
        Assertions.assertThat(id).isNotNull();
    }

    @Test
    void itCantCreateATaskWithAValidProject() {
        Project project = new Project(1L,"BirdIn Software", "This is a project", "sarah1");
        restTemplate
                .withBasicAuth("sarah1", "abc123")
                .postForEntity("/projects", project, Void.class);
        Task task = new Task("BirdIn Software", "This is a task", "sarah1");
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

        Assertions.assertThat(responseGetTask.getStatusCode()).isEqualTo(HttpStatus.OK);


    }

    void itReturnsATaskBasedOnTheProject() {
    }

    void itCantReturnATaskBasedOnTheProject() {

    }


    }