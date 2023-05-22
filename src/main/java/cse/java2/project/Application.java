package cse.java2.project;

import cse.java2.project.service.QuestionAndAnswerService;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * This is the main class of the Spring Boot application.
 */

@SpringBootApplication
@ComponentScan("cse.java2.project.repository") // 包路径
@ComponentScan("cse.java2.project.service") // 包路径
@EnableJpaRepositories(basePackages = "cse.java2.project.repository")
public class Application implements WebMvcConfigurer {

  private final QuestionAndAnswerService questionService;

  public Application(QuestionAndAnswerService questionService) {
    this.questionService = questionService;
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @PostConstruct
  public void init() {
    questionService.saveData();
  }


  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/demo").setViewName("demo");
    registry.addViewController("/answer").setViewName("answer");


  }

}
