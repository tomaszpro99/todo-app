package io.github.tomaszpro99.controller;

import io.github.tomaszpro99.logic.TaskGroupService;
import io.github.tomaszpro99.model.Task;
import io.github.tomaszpro99.model.TaskRepository;
import io.github.tomaszpro99.model.projection.GroupReadModel;
import io.github.tomaszpro99.model.projection.GroupWriteModel;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/groups")
class TaskGroupController {
    private static final Logger logger = LoggerFactory.getLogger(TaskGroupController.class);
    private final TaskGroupService service;
    private final TaskRepository repository;
    public TaskGroupController(final TaskGroupService service, final TaskRepository repository) {
        this.service = service;
        this.repository = repository;
    }
    @PostMapping
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel toCreate) {
        GroupReadModel result = service.createGroup(toCreate);
        return ResponseEntity.created(URI.create("/"+result.getId())).body(result);
    }
    @GetMapping
    ResponseEntity<List<GroupReadModel>> readAllGroups() {
        return ResponseEntity.ok(service.readAll());
    }
    @GetMapping("/{id}")
    ResponseEntity<List<Task>> readAllTasksFromGroup(@PathVariable int id) {
        return ResponseEntity.ok(repository.findAllByGroup_Id(id));
    }
    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleGroup(@PathVariable int id) {
        service.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }
    //nie ma grupy = 404 not found
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }
    //
    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalState(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
