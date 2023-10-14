package io.github.tomaszpro99.controller;

import io.github.tomaszpro99.model.Task;
import io.github.tomaszpro99.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerE2ETest { //EndToEnd
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    TaskRepository repo;
    @Test
    void httpGet_returnsAllTasks() {
        //given
        int initial = repo.findAll().size();
        repo.save(new Task("testE2E", LocalDateTime.now()));
        repo.save(new Task("testE2E2", LocalDateTime.now()));
        //when
        Task[] result = restTemplate.getForObject("http://localhost:" + port + "/tasks", Task[].class);
        //then
        assertThat(result).hasSize(initial + 2);
    }
}