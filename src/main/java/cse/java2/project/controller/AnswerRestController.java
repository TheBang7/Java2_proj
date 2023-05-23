package cse.java2.project.controller;

import cse.java2.project.model.Answer;
import cse.java2.project.model.Comment;
import cse.java2.project.model.Question;
import cse.java2.project.service.QuestionAndAnswerService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnswerRestController {

  private final QuestionAndAnswerService questionService;

  public AnswerRestController(QuestionAndAnswerService questionService) {
    this.questionService = questionService;
  }

  @GetMapping("/json/answers")
  public List<Answer> getAnswers(
      @RequestParam(value = "isAccepted", required = false) Boolean isAccepted,
      @RequestParam(value = "questionId", required = false) Integer questionId) {
    List<Answer> answers = questionService.getAnswersBy(isAccepted, questionId);
    return answers;
  }

  @GetMapping("/json/questions")
  public List<Question> getFilteredQuestions(
      @RequestParam(value = "isAnswered", required = false) Boolean isAnswered,
      @RequestParam(value = "id", required = false) Integer id) {
    return questionService.getFilteredQuestions(isAnswered, id);
  }

  @GetMapping("/json/comments")
  public List<Comment> getFilteredComments(
      @RequestParam(value = "postId", required = false)Integer postId,
      @RequestParam(value = "id", required = false) Integer id) {
    return questionService.getFilteredComments(postId, id);
  }



}
