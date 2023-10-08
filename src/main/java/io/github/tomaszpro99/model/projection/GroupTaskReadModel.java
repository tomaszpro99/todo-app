package io.github.tomaszpro99.model.projection;

import io.github.tomaszpro99.model.Task;

public class GroupTaskReadModel {

    private String description;
    private boolean done;

    public GroupTaskReadModel(Task source) {
        description = source.getDescription();
        done = source.isDone();
    }
    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
    public boolean isDone() {return done;}
    public void setDone(boolean done) {this.done = done;}
}
