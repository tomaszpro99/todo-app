package io.github.tomaszpro99.adapter; // TaskRepository - klasa ktora sluzy do komunikacji z baza danych
//api - punkt wejscia do dzialania na zbiorze

import io.github.tomaszpro99.model.Task;
import io.github.tomaszpro99.model.TaskRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//@RepositoryRestResource //chcemy zeby Spring wszedl tutaj do tego pliku i go rozpoznal // tworzymy repozytorium i caly interface Rest'owy - czyli udostepnimy na zewnatrz wszystkie metody, http, tworzenia, aktualizowania
@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Integer> { //Jpa - stad Sql

    @Override
    @Query(nativeQuery = true, value = "select count(*) > 0 from tasks where id=:id")
    boolean existsById(@Param("id") Integer id);

    /*@Override
    boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);*/
}

