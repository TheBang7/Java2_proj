package cse.java2.project.Data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import cse.java2.project.model.Answer;
import cse.java2.project.model.Comment;
import cse.java2.project.model.Question;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class getComments {

  public static void main(String[] args) throws IOException {
    OkHttpClient client = new OkHttpClient();

    // Read questions from JSON file
    String jsonStrings = Files.readString(Path.of("questions.json"));
    Type questionListType = new TypeToken<List<Question>>() {
    }.getType();
    List<Question> questions = new Gson().fromJson(jsonStrings, questionListType);

    List<Comment> comments = new ArrayList<>();
    boolean f = true;
    for (int i = 0; f && i < questions.size(); i += 100) {
      List<Question> batchQuestions = questions.subList(i, Math.min(i + 100, questions.size()));

      String questionIds = getQuestionIds(batchQuestions);
      int pageNumber = 1;
      boolean hasMoreAnswers;

      do {
        String url = String.format(
            "https://api.stackexchange.com/2.3/questions/%s/comments?order=desc&sort=creation&page=%d&site=stackoverflow&filter=!nOedRLmSmS",
            questionIds, pageNumber);

        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
          String responseBody = null;
          if (response.body() != null) {
            responseBody = response.body().string();
          }
          if (responseBody == null || responseBody.length() == 0 || responseBody.contains(
              "error_id") || responseBody.contains(
              "\"has_more\": false")) {
            System.out.println(responseBody);
            System.out.println(i);
            f = false;
            break;
          }
          Gson gson = new Gson();
          getComments.StackExchangeResponse stackExchangeResponse = gson.fromJson(responseBody,
              getComments.StackExchangeResponse.class);

          List<Comment> page = stackExchangeResponse.getComments();
          if (page != null && !page.isEmpty()) {
            comments.addAll(page);
          }

          hasMoreAnswers = page == null || page.size() > 0;
          pageNumber++;
        }
      } while (hasMoreAnswers);
      System.out.println(i);
    }

    System.out.println("Total comments: " + comments.size());

    // Save answers to a file
    String json = new Gson().toJson(comments);
    FileWriter writer = new FileWriter("comments.json");
    writer.write(json);
    writer.close();
  }

  private static String getQuestionIds(List<Question> questions) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < questions.size(); i++) {
      if (i > 0) {
        sb.append(";");
      }
      sb.append(questions.get(i).getId());
    }
    return sb.toString();
  }

  static class StackExchangeResponse {

    @SerializedName("items")
    private List<Comment> comments;

    public List<Comment> getComments() {
      return comments;
    }


  }

}
