package cse.java2.project.service;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cse.java2.project.model.Answer;
import cse.java2.project.model.Question;
import cse.java2.project.repository.AnswerRepository;
import cse.java2.project.repository.QuestionRepository;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
@ComponentScan("cse.java2.project.repository") // 包路径
public class QuestionAndAnswerService {

  private final QuestionRepository questionRepository;
  private final AnswerRepository answerRepository;

  @Autowired
  public QuestionAndAnswerService(QuestionRepository questionRepository,
      AnswerRepository answerRepository) {
    this.questionRepository = questionRepository;
    this.answerRepository = answerRepository;

  }

  public void saveData() {
    try {
      String jsonStrings = Files.readString(Path.of("questions.json"));
      Type questionListType = new TypeToken<List<Question>>() {
      }.getType();
      List<Question> questions = new Gson().fromJson(jsonStrings, questionListType);
      questionRepository.saveAll(questions);

      String jsonStrings2 = Files.readString(Path.of("answers.json"));
      Type answerListType = new TypeToken<List<Answer>>() {
      }.getType();
      List<Answer> answers = new Gson().fromJson(jsonStrings2, answerListType);
      answerRepository.saveAll(answers);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public List<Question> getAllQuestions() {
    return questionRepository.findAll();
  }

  public double getPercentageOfUnansweredQuestions() {
    return (double) questionRepository.countQuestionsWithoutAnswers()
        / questionRepository.countQuestions();
  }

  public double getAverageNumberOfAnswers() {
    return questionRepository.avgNumberOfAnswers();
  }

  public int getMaximumNumberOfAnswers() {
    return questionRepository.maxNumberOfAnswers();
  }

  public List<Object[]> getDistributionOfAnswerCounts() {
    return questionRepository.numberOfAnswersDistribution();
  }

  public double getPercentageOfQuestionWithAcceptedAnswer() {
    return (double) questionRepository.countQuestionsWithAcceptedAnswer()
        / questionRepository.countQuestions();
  }

  public Map<String, Integer> getDistributionOfAnswerTime() {
    List<Object[]> questions = questionRepository.QuestionListWithAcceptedAnswer();
    Map<String, Integer> answerTimeDistribution = new HashMap<>();

    for (Object[] objects : questions) {
      Integer questionId = (Integer) objects[1];
      Long answerCreationDateMinutes = answerRepository.getCreationDateOfAcceptedAnswer(questionId);

      if (answerCreationDateMinutes != null) {
        Long questionCreationDateMinutes = (Long) objects[0];
        LocalDateTime questionCreationDate = LocalDateTime.ofInstant(
            Instant.ofEpochSecond(questionCreationDateMinutes * 60), ZoneId.systemDefault());
        LocalDateTime answerDateTime = LocalDateTime.ofInstant(
            Instant.ofEpochSecond(answerCreationDateMinutes * 60), ZoneId.systemDefault());
        Duration resolutionTime = Duration.between(questionCreationDate, answerDateTime);
        long resolutionTimeMinutes = resolutionTime.toMinutes();

        String timeRange = getTimeRange(resolutionTimeMinutes);
        answerTimeDistribution.put(timeRange,
            answerTimeDistribution.getOrDefault(timeRange, 0) + 1);
      }
    }

    return answerTimeDistribution;
  }

  private String getTimeRange(long resolutionTimeMinutes) {
    if (resolutionTimeMinutes < 12 * 60) {
      return "#1: <12h";
    } else if (resolutionTimeMinutes <= 24 * 60) {
      return "#2: 12-24h";
    } else if (resolutionTimeMinutes <= 3 * 24 * 60) {
      return "#3: 1-3days";
    } else if (resolutionTimeMinutes <= 7 * 24 * 60) {
      return "#4: 3-7days";
    } else if (resolutionTimeMinutes <= 30 * 24 * 60) {
      return "#5: 7-30days";
    } else {
      return "#6: >30days";
    }
  }


}
