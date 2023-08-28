package io.github.tomaszpro99.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

//TODO: hej

@Entity //bedzie tabela odpowiadajaca klasie
@Table(name = "tasks")
public class Task {
    @Id //oznaczamy id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//IDENTITY //adnotacja do id - strategia generowania, id bedzie kazdorazowo powstawal, sekwencyjne nadawanie id, honorwanie
    private int id;
    //@Column(name = "desc") //Mapujemy pola ALBO getery,setery //Adnotacje na polach ALBO nizej na geterach
    @NotBlank(message = "Task's description must not be empty")
    private String description;
    private boolean done;
    public Task() {
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    //@Column(name = "desc") //Mapujemy pola ALBO getery,setery //Adnotacje na polach ALBO wyzej na geterach
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    //@Column(name="done")
    public boolean isDone() { return done; }
    public void setDone(boolean done) { this.done = done; }
}
