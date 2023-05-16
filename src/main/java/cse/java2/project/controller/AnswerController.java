package cse.java2.project.controller;

import cse.java2.project.repository.QuestionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnswerController {

  @Autowired
  private QuestionRepository questionRepository;

  @GetMapping({"/answer"})
  public String answer(Model model) {
    // Calculate the required statistics
    long countQuestionsWithoutAnswers = questionRepository.countQuestionsWithoutAnswers();
    double avgNumberOfAnswers = questionRepository.avgNumberOfAnswers();
    int maxNumberOfAnswers = questionRepository.maxNumberOfAnswers();
    List<Object[]> numberOfAnswersDistribution = questionRepository.numberOfAnswersDistribution();
//    System.out.println("1 " +
//        countQuestionsWithoutAnswers + " " + avgNumberOfAnswers + " " + maxNumberOfAnswers);
    // Set the model attributes for the view
    model.addAttribute("percentageWithoutAnswer",
        (countQuestionsWithoutAnswers * 100) / questionRepository.count());
    model.addAttribute("averageAnswers", avgNumberOfAnswers);
    model.addAttribute("maximumAnswers", maxNumberOfAnswers);
    model.addAttribute("answersDistribution", numberOfAnswersDistribution);

    return "answer";
  }
}
