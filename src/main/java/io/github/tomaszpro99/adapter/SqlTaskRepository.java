package io.github.tomaszpro99.adapter; // TaskRepository - klasa ktora sluzy do komunikacji z baza danych
//api - punkt wejscia do dzialania na zbiorze

import io.github.tomaszpro99.model.Task;
import io.github.tomaszpro99.model.TaskRepository;
import  org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

//public class TaskRepository {
//@RepositoryRestResource(path = "todos",collectionResourceRel = "todos")
//@RepositoryRestResource //chcemy zeby Spring wszedl tutaj do tego pliku i go rozpoznal // tworzymy repozytorium i caly interface Rest'owy - czyli udostepnimy na zewnatrz wszystkie metody, http, tworzenia, aktualizowania
@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Integer> { //Jpa - stad Sql
//nie mamy tu dostepu ze wzgledu na pakietowy dostep

    //uniemozliwamiy usuniecie taska //ukrywamy metody //406 Method Not Allowed
//    @Override
//    @RestResource(exported = false)
//    void deleteById(Integer integer); //usuwa po id
//
//    @Override
//    @RestResource(exported = false)
//    void delete(Task task); //usuwa encje
//
//    @RestResource(path = "done", rel = "done") //zmiana nazwy adr -> path-w adr url ,rel-w laczach
//    //List<Task> findByDoneIsTrue(); //metoda zwracajaca liste<Taskow> //znajdz wszystkie taski ustawione na true  //zmieniamy na uniwersalna t/f
//    List<Task> findByDone(@Param("state")boolean done); //Nie podalismy wartosci dla flagi Done, to musi zostac przekazana jako parametr boolean
//    //dzieki temu mozemy sobie wyszukiwac localhost:8080/tasks/search/done?state=false

    @Override
    @Query(nativeQuery = true, value = "select count(*) > 0 from tasks where id=:id")
    boolean existsById(@Param("id") Integer id);

    @Override
    boolean existsByDoneIsFalseAndGroup_Id (Integer groupId);

    @Override
    List<Task> findAllByGroup_Id(Integer groupId);
}

