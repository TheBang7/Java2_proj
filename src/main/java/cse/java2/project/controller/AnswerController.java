package cse.java2.project.controller;

import cse.java2.project.service.QuestionAndAnswerService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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

  @GetMapping({"/", "/answer"})
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

    double PercentageOfAnswerUpvote = questionService.getPercentageOfAnswerUpvote();
    model.addAttribute("PercentageOfAnswerUpvote", PercentageOfAnswerUpvote);

    long questionCount = questionService.getQuestionCount();
    model.addAttribute("questionCount", questionCount);

    List<Map<String, Object>> topTenTagsWithCount = questionService.getTopTenTagsWithCount();
    model.addAttribute("topTenTagsWithCount", topTenTagsWithCount);

    Map<String, Integer> topTagsWithUpvote = questionService.getTopTenTagsWithUpvote();
    model.addAttribute("topTagsWithUpvote", topTagsWithUpvote);

    Map<String, Integer> topTagsWithView = questionService.getTopTenTagsWithView();
    model.addAttribute("topTagsWithView", topTagsWithView);

    return "answer";


  }

}

