package cse.java2.project.service;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cse.java2.project.model.Question;
import cse.java2.project.repository.QuestionRepository;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

  private final QuestionRepository questionRepository;

  @Autowired()
  public QuestionService(QuestionRepository studentRepository) {
    this.questionRepository = studentRepository;
  }

  public void saveQuestions() {
    try {
      String jsonStrings = Files.readString(Path.of("questions.json"));
      Type questionListType = new TypeToken<List<Question>>() {
      }.getType();
      List<Question> questions = new Gson().fromJson(jsonStrings, questionListType);
      questionRepository.saveAllAndFlush(questions);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  public List<Question> getQuestions() {
    return questionRepository.findAll();
  }
}
