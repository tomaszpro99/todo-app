package io.github.tomaszpro99;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("task")
public class TaskConfigurationProperties {
//    private boolean allowMultipleTasksFromTemplate;
//    //get er
//    public boolean isAllowMultipleTasksFromTemplate() { return allowMultipleTasksFromTemplate; }
//    //set er
//    public void setAllowMultipleTasksFromTemplate(final boolean allowMultipleTasksFromTemplate) {
//        this.allowMultipleTasksFromTemplate = allowMultipleTasksFromTemplate;
//    }

    private Template template;

    public Template getTemplate() { //geter
        return template;
    }

    public void setTemplate(Template template) { //seter
        this.template = template;
    }

    public static class Template {
        private boolean allowMultipleTasks;

        public boolean isAllowMultipleTasks() { //geter
            return allowMultipleTasks;
        }

        public void setAllowMultipleTasks(boolean allowMultipleTasks) { //seter
            this.allowMultipleTasks = allowMultipleTasks;
        }
    }
}
