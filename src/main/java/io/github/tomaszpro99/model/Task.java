package io.github.tomaszpro99.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

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
    @Column()
    private LocalDateTime deadline;
    @Embedded
    private Audit audit = new Audit();
    /*@ManyToOne
    @JoinColumn(name = "task_group_id")
    private TaskGroup group;*/
    public Task() {
    }
    public int getId() { return id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isDone() { return done; }
    public void setDone(boolean done) { this.done = done; }

    public LocalDateTime getDeadline() {
        return deadline;
    }
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
    /*public TaskGroup getGroup() {
        return group;
    }
    public void setGroup(TaskGroup group) {
        this.group = group;
    }*/
    public void updateFrom(final Task source) {
        description = source.description;
        done = source.done;
        deadline = source.deadline;
        /*group = source.group;*/
    }
}
