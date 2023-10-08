package io.github.tomaszpro99.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "task_groups")
public class TaskGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Task's description must not be empty")
    private String description;
    private boolean done;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private Set<Task> tasks;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    public TaskGroup() {
    }
    public int getId() { return id; }
    public void setId(final int id) {this.id = id;}
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isDone() { return done; }
    public void setDone(boolean done) { this.done = done; }
    public Set<Task> getTasks() {return tasks;}
    public void setTasks(Set<Task> tasks) {this.tasks = tasks;}
    public Project getProject() {return project;}
    public void setProject(Project project) {this.project = project;}
}
