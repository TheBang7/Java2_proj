package cse.java2.project;

import cse.java2.project.service.QuestionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This is the main class of the Spring Boot application.
 */
@ComponentScan("cse.java2.project.repository")
@SpringBootApplication
public class Application implements WebMvcConfigurer {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/demo").setViewName("demo");
    registry.addViewController("/answer").setViewName("answer");
  }

}
