package com.example.birdinbackend.project;

import com.example.birdinbackend.project.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest

public class ProjectJsonTest {

    @Autowired
    private JacksonTester<Project> json;
    @Test
    public void testTestSuite() {
        assertThat(1).isEqualTo(1);
    }

    @Test
    public void testProjectJson() throws IOException {
        Project project = new Project(1L,"BirdIn Software", "This is a project");
        assertThat(json.write(project)).isStrictlyEqualToJson("expected.json");

    }
}
