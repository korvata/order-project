package kr.co._29cm.homework.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

	private final ApplicationContext applicationContext;
	private final Display display;

	public BackendApplication(ApplicationContext applicationContext, Display display) {
		this.applicationContext = applicationContext;
		this.display = display;
	}

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Environment environment = applicationContext.getEnvironment();
		if(environment.getProperty("app.about") != "test"){
			display.start();
			SpringApplication.exit(applicationContext);
		}
	}
}
