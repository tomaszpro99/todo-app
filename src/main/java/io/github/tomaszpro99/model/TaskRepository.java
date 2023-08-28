package io.github.tomaszpro99.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> findAll();
    Page<Task> findAll(Pageable page);
    Optional<Task> findById(Integer id);//chcemy tylko konkretny task
    boolean existsById(Integer id); //metoda z CrudRepository- czy dany id istnieje
    List<Task> findByDone(boolean done);
    Task save(Task entity); //task do zapisania, stworzenia
}
