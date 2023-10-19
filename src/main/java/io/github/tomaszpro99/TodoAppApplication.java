package io.github.tomaszpro99;

//import org.springframework.validation.Validator; //zmiana na jakarta
import jakarta.validation.Validator; //jakarta dla Spring 3+
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
//import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
//import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

//@EnableConfigurationProperties(TaskConfigurationProperties.class)
@EnableAsync //musi byc do działania @Async
@SpringBootApplication
public class TodoAppApplication /*implements RepositoryRestConfigurer*/ {
	public static void main(String[] args) { SpringApplication.run(TodoAppApplication.class, args); }
	@Bean //klasa z konfiguracja
	Validator validator() { return new LocalValidatorFactoryBean(); }//obiekt zwrocony/utworzony z tej metody, typu Validator bedzie klasa zarzadzalna przez Springa
	//rezygnacja z RepositoryRestResource - usunelismy wszystkie walidacje poza validator, ale trzeba mu powieziec zeby dzialal w @ReguestBody

	/*@Override
	public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
		validatingListener.addValidator("beforeCreate",validator());
		validatingListener.addValidator("beforeSave",validator());
	}*/
}
