package io.github.tomaszpro99.adapter;

import io.github.tomaszpro99.model.Project;
import io.github.tomaszpro99.model.ProjectRepository;
import io.github.tomaszpro99.model.TaskGroup;
import io.github.tomaszpro99.model.TaskGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Integer> {

    @Override
    @Query("select distinct p from Project p join fetch p.steps")
    List<Project> findAll();
}
