package cse.java2.project.controller;

import cse.java2.project.service.QuestionAndAnswerService;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnswerController {

  private final QuestionAndAnswerService questionService;

  public AnswerController(QuestionAndAnswerService questionService) {
    this.questionService = questionService;
  }

  @GetMapping("/answer")
  public String answer(Model model) {

    double percentageOfUnansweredQuestions = questionService.getPercentageOfUnansweredQuestions();
    double averageNumberOfAnswers = questionService.getAverageNumberOfAnswers();
    int maximumNumberOfAnswers = questionService.getMaximumNumberOfAnswers();
    List<Object[]> distributionOfAnswerCounts = questionService.getDistributionOfAnswerCounts();

    model.addAttribute("percentageOfUnansweredQuestions", percentageOfUnansweredQuestions);
    model.addAttribute("averageNumberOfAnswers", averageNumberOfAnswers);
    model.addAttribute("maximumNumberOfAnswers", maximumNumberOfAnswers);
    model.addAttribute("distributionOfAnswerCounts", distributionOfAnswerCounts);

    double percentageOfQuestionWithAcceptedAnswer = questionService.getPercentageOfQuestionWithAcceptedAnswer();
    model.addAttribute("percentageOfQuestionWithAcceptedAnswer",
        percentageOfQuestionWithAcceptedAnswer);//What percentage of questions have accepted answers

    Map<String, Integer> distributionOfAnswerTime = questionService.getDistributionOfAnswerTime();
    model.addAttribute("distributionOfAnswerTime", distributionOfAnswerTime);

//    for (String s : distributionOfAnswerTime.keySet()) {
//      System.out.println(s + " " + distributionOfAnswerTime.get(s));
//    }

    return "answer";
  }

}

