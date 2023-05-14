package cse.java2.project.Data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import cse.java2.project.model.Question;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class getQuestions {

  public static void main(String[] args) throws IOException {
    OkHttpClient client = new OkHttpClient();

    String baseUrl = "https://api.stackexchange.com/2.3/questions?order=desc&sort=activity&site=stackoverflow&tagged=java&pagesize=100&page=%d";

    int currentPage = 1;
    int totalQuestions = 0;

    List<Question> questions = new ArrayList<>();
    try (FileWriter file = new FileWriter("test.json")) {
    while (true) {
      String url = String.format(baseUrl, currentPage);
      Request request = new Request.Builder().url(url).build();

      try (Response response = client.newCall(request).execute()) {
        String responseBody = null;
        if (response.body() != null) {
          responseBody = response.body().string();
        }

        Gson gson = new Gson();
        StackExchangeResponse stackExchangeResponse = gson.fromJson(responseBody,
            StackExchangeResponse.class);

        List<Question> pageQuestions = stackExchangeResponse.getQuestions();
        if (pageQuestions == null || pageQuestions.isEmpty()) {
          break;
        }
        totalQuestions += pageQuestions.size();

        questions.addAll(pageQuestions);
        currentPage++;
      }
    }}

    System.out.println("Total questions: " + questions.size());

    // Save questions to a file
    String json = new Gson().toJson(questions);
    FileWriter writer = new FileWriter("questions.json");
    writer.write(json);
    writer.close();
  }
}

class StackExchangeResponse {

  @SerializedName("items")
  private List<Question> questions;

  public List<Question> getQuestions() {
    return questions;
  }
}
