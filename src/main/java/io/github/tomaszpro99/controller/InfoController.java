package io.github.tomaszpro99.controller;

import io.github.tomaszpro99.TaskConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class InfoController {
    //@Autowired
    private DataSourceProperties dataSource;
    //@Value("${task.allowMultipleTasksFromTemplate}")
    private TaskConfigurationProperties myProp;

    //construktor wstrzyknie nam te rzeczy
    public InfoController(final DataSourceProperties dataSource, final TaskConfigurationProperties myProp) {
        this.dataSource = dataSource;
        this.myProp = myProp;
    }

    @GetMapping("info/url")
    String url() {
        return dataSource.getUrl();
    }

    @GetMapping("info/prop")
    boolean myProp() {
        return myProp.getTemplate().isAllowMultipleTasks();
    }
}
