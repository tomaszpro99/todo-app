package io.github.tomaszpro99.model; // TaskRepository - klasa ktora sluzy do komunikacji z baza danych
//api - punkt wejscia do dzialania na zbiorze

import  org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

//public class TaskRepository {
//@RepositoryRestResource(path = "todos",collectionResourceRel = "todos")
@RepositoryRestResource //chcemy zeby Spring wszedl tutaj do tego pliku i go rozpoznal // tworzymy repozytorium i caly interface Rest'owy - czyli udostepnimy na zewnatrz wszystkie metody, http, tworzenia, aktualizowania
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Integer> { //Jpa - stad Sql
//nie mamy tu dostepu ze wzgledu na pakietowy dostep

    //uniemozliwamiy usuniecie taska //ukrywamy metody //406 Method Not Allowed
    @Override
    @RestResource(exported = false)
    void deleteById(Integer integer); //usuwa po id

    @Override
    @RestResource(exported = false)
    void delete(Task task); //usuwa encje

    @RestResource(path = "done", rel = "done") //zmiana nazwy adr -> path-w adr url ,rel-w laczach
    //List<Task> findByDoneIsTrue(); //metoda zwracajaca liste<Taskow> //znajdz wszystkie taski ustawione na true  //zmieniamy na uniwersalna t/f
    List<Task> findByDone(@Param("state")boolean done); //Nie podalismy wartosci dla flagi Done, to musi zostac przekazana jako parametr boolean
    //dzieki temu mozemy sobie wyszukiwac localhost:8080/tasks/search/done?state=false

}

