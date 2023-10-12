package io.github.tomaszpro99.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

@Entity
@Table(name = "tasks")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Project's description must not be empty")
    private String description;
    @OneToMany(mappedBy = "project")
    private Set<TaskGroup> groups;
    @OneToMany(mappedBy = "project")
    private Set<ProjectStep> steps;

    Project() {
    }

    public int getId() {return id;}
    void setId(int id) {this.id = id;}
    public String getDescription() {return description;}
    void setDescription(String description) {this.description = description;}
    Set<TaskGroup> getGroups() {return groups;}
    void setGroups(Set<TaskGroup> groups) {this.groups = groups;}
    public Set<ProjectStep> getSteps() {return steps;}
    void setSteps(Set<ProjectStep> steps) {this.steps = steps;}
}
