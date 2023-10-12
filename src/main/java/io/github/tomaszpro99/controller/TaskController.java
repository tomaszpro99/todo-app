package io.github.tomaszpro99.controller;

import io.github.tomaszpro99.model.Task;
//import io.github.tomaszpro99.model.SqlTaskRepository;
import io.github.tomaszpro99.model.TaskRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
//import org.springframework.data.rest.webmvc.RepositoryRestController; //powiazemy klase z naszym istniejacym repozytorium
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

//spring szuka tych class @Repository
//@RestController //zmiana - rozwoj - adnotacja springowa - teraz jedynie mozemy odczytywac taski
@RestController //abstrakcja - znacznik pozwalajacy dostac sie do kontrolera, ktory jest uzywany przez spring mvc
class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class); //beda tworzone logi z klasy TaskController
    private final TaskRepository repository; //potrzebujemy repozytorium na ktorym dzialamy //tworzymy aplikacje ktora nie bedzie upubliczniac TaskRepository

    //@Autowired wstrzyknij tutaj repozytorium do konstruktora, juz nie potrzebne
    TaskController(final TaskRepository repository) {this.repository = repository;}
    //dostepne repozytorium - mozemy korzystac - nadpisujemy -- abstrakcje //metoda zwracajaca wszystkie taski, + info

    @PostMapping("/tasks")
    ResponseEntity<Task> createTask(@RequestBody @Valid Task toCreate) {
        Task result = repository.save(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }
    //@RequestMapping(method = RequestMethod.GET, path = "/tasks") //Nadpisujemy metode mapowania(jaki reguest? z metoda GET, oraz ze sciezka /tasks)
    @GetMapping(value = "/tasks", params = {"!sort", "!page", "!size"})
    //niektore requesty maja dedykowane mappingi //params - zgodnie ze sztuka do sortowania/stronnicowania danych, mapujemy gdy nie ma tych parametrow
    ResponseEntity<List<Task>> readAllTasks() {//spring zmieni obiekt javowy na: lista - json //zostawiamy ?(niewiadomy), nie jest to zbyw zawily przypadek - dopiero przy drugim uzywamy
        //ResponseEntity<?> readAllTasks() { //ResponseEntity reprezentuje odp //readAllTaska metoda bezparametrowa ktora zawola loggerem

        logger.warn("Exposing all the tasks!");
        //if (true) { return ResponseEntity.notFound(); } //dzialaloby z List<Task> + //return repository.findAll();
        return ResponseEntity.ok(repository.findAll()); //zwracamy z metody fabrykujacej status ok //odp to co zwraca findAll
    }

    @GetMapping("/tasks")
    ResponseEntity<?> readAllTasks(Pageable page) { //Pageable - struktura danych - obiekt //Spring se wstrzyknie
        logger.info("Custom Pageable");
        return ResponseEntity.ok(repository.findAll(page).getContent()); //dla danej strony bierzemy Content
    }

    @GetMapping("/tasks/{id}")
    ResponseEntity<Task> readTask(@PathVariable int id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/tasks/{id}") //mozna w springu oznaczyc id //naiwna implementacja - stworzy id jak nie ma
    ResponseEntity<?> updateTaks(@PathVariable int id, @RequestBody @Valid Task toUpdate) {//w rzadaniu metoda PUT znajduje sie cialo i z tego ciala chcemy wziac zawartosc - nowa reprezentacja taska //rzadanie ma przej validacje - pusty sting - blad 400 "Tasks descr must not be empty"
        if(!repository.existsById(id)) {//id definiowany w adr url, wiec mozemy sie do niego odwolac -@PathVariable - pozwala wziac z adr jakas zmienna - id
            return ResponseEntity.notFound().build(); //jezeli nie ma id - notFound
        }
        repository.findById(id)
                .ifPresent(task -> {
                    task.updateFrom(toUpdate);
                    repository.save(task);
                });
        //toUpdate.setId(id);
        //repository.save(toUpdate); //w CrudRepository nie ma znaczenia czy tworzymy czy zapisujemy - jedna metoda
        return ResponseEntity.noContent().build(); //skoro sie wszystko udalo - noContent - budujemy
    }

    @Transactional //kazda metoda tak oznaczona: na poczatku transaction begin, na koncu transaction commit
    @PatchMapping("/tasks/{id}") //zmienianie putch f -> t, t -> f
    public ResponseEntity<?> toggleTaks(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.findById(id)
                .ifPresent(task -> task.setDone(!task.isDone()));
        //throw new RuntimeException(); //wycofanie transaction
        return ResponseEntity.noContent().build();
    }
}
