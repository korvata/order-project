package kr.co._29cm.homework.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

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
		display.start();
		SpringApplication.exit(applicationContext);
	}
}
