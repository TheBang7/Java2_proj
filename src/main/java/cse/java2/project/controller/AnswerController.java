package cse.java2.project.controller;

import cse.java2.project.model.Question;
import cse.java2.project.service.QuestionAndAnswerService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AnswerController {

  private final QuestionAndAnswerService questionService;

  public AnswerController(QuestionAndAnswerService questionService) {
    this.questionService = questionService;
  }

  @GetMapping({"/", "/answer"})
  public String answer(Model model) {

    return "answer";

  }

  @GetMapping("/question")
  public String question(Model model) {

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
        percentageOfQuestionWithAcceptedAnswer); //What percentage of questions have accepted answers

    Map<String, Integer> distributionOfAnswerTime = questionService.getDistributionOfAnswerTime();
    model.addAttribute("distributionOfAnswerTime", distributionOfAnswerTime);

    double PercentageOfAnswerUpvote = questionService.getPercentageOfAnswerUpvote();
    model.addAttribute("PercentageOfAnswerUpvote", PercentageOfAnswerUpvote);

    long questionCount = questionService.getQuestionCount();
    model.addAttribute("questionCount", questionCount);

    return "question";
  }

  @GetMapping("/tags")
  public String tags(Model model) {

    List<Map<String, Object>> topTenTagsWithCount = questionService.getTopTenTagsWithCount();
    model.addAttribute("topTenTagsWithCount", topTenTagsWithCount);

    List<Object[]> topTagsWithUpvote = questionService.getTopTenTagsWithUpvote();
    model.addAttribute("topTagsWithUpvote", topTagsWithUpvote);

    List<Object[]> topTagsWithView = questionService.getTopTenTagsWithView();
    model.addAttribute("topTagsWithView", topTagsWithView);
    return "tags";

  }

  @GetMapping("/user")
  public String users(Model model) {

    Map<String, Long> userDistribution = questionService.userDistribution();
    model.addAttribute("userDistribution", userDistribution);
    for (String s : userDistribution.keySet()) {
      System.out.println(s + " " + userDistribution.get(s));
    }

    List<Object[]> TopUsers = questionService.TopUsers();
    model.addAttribute("TopUsers", TopUsers);

    List<Object[]> TopUsersWithMostComments = questionService.TopUsersWithMostComments();
    model.addAttribute("TopUsersWithMostComments", TopUsersWithMostComments);

    List<Object[]> TopUsersWithMostAnswers = questionService.TopUsersWithMostAnswers();
    model.addAttribute("TopUsersWithMostAnswers", TopUsersWithMostAnswers);

    return "user";

  }

  @GetMapping("/bonus")
  public String bonus(Model model) {

    List<Object[]> topClassAndMethod = questionService.BonusTopClass();
    model.addAttribute("topClassAndMethod", topClassAndMethod);


    return "bonus";

  }


}

