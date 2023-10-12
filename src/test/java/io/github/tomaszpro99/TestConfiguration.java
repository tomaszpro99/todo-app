package io.github.tomaszpro99;

import io.github.tomaszpro99.model.Task;
import io.github.tomaszpro99.model.TaskRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

@Configuration
public class TestConfiguration {
    @Bean
//    @Primary
//    @ConditionalOnMissingBean
    @Profile("integration")
    TaskRepository testRepo() {
        return new TaskRepository() {
            private Map<Integer, Task> tasks = new HashMap<>();
            @Override
            public List<Task> findAll() { return new ArrayList(tasks.values()); }
            @Override
            public Page<Task> findAll(Pageable page) {return null;}
            @Override
            public Optional<Task> findById(Integer id) { return Optional.ofNullable(tasks.get(id)); }
            @Override
            public boolean existsById(Integer id) { return tasks.containsKey(id); }
            @Override
            public List<Task> findByDone(boolean done) {return null;}
            @Override
            public boolean existsByDoneIsFalseAndGroup_Id(Integer groupId) {return false;}
            @Override
            public Task save(Task entity) { return tasks.put(tasks.size() +1, entity ); }
        };
    }
}
