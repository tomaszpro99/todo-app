package io.github.tomaszpro99.logic;

import io.github.tomaszpro99.model.TaskGroup;
import io.github.tomaszpro99.model.TaskGroupRepository;
import io.github.tomaszpro99.model.TaskRepository;
import io.github.tomaszpro99.model.projection.GroupReadModel;
import io.github.tomaszpro99.model.projection.GroupWriteModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service //warstwa logiki, posrednia miedzy repo a kontrolerem //tutaj miedzy dwoma repo
public class TaskGroupService {
    private TaskGroupRepository repository;
    private TaskRepository taskRepository;
    public TaskGroupService(TaskGroupRepository repository, TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }
    //metoda tworzaca grupe z writemodel
    public GroupReadModel createGroup(GroupWriteModel source) {
        TaskGroup result = repository.save(source.toGroup());
        return new GroupReadModel(result);
    }
    public List<GroupReadModel> readAll() {
        return repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }
    //zakonczenie grupy
    public void toggleGroup(int groupId) {
        if (taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)) {
            throw new IllegalStateException("Group has undone tasks. Done all the tasks first");
        }
        TaskGroup result = repository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("TaskGroup with given id not found"));
        result.setDone(!result.isDone());
    }
}
