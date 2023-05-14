package cse.java2.project.Data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cse.java2.project.model.Question;
import cse.java2.project.service.QuestionService;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class dealData {

  public static void main(String[] args) {
    try {
      String jsonStrings = Files.readString(Path.of("questions.json"));
      Type questionListType = new TypeToken<List<Question>>() {
      }.getType();
      List<Question> questions = new Gson().fromJson(jsonStrings, questionListType);
      //从json文件中获取从网上爬取的2500个问题，将其保存在本地


    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
