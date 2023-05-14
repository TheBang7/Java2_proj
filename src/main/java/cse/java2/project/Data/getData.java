package cse.java2.project.Data;

import java.io.FileWriter;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class getData {

  public static void main(String[] args) throws IOException {
    OkHttpClient client = new OkHttpClient();

    String baseUrl = "https://api.stackexchange.com/2.3/questions?order=desc&sort=activity&site=stackoverflow&tagged=java&pagesize=100&page=%d";

    int currentPage = 1;
    int totalQuestions = 0;
    String url = String.format(baseUrl, currentPage);
    Request request = new Request.Builder().url(url).build();

    try (FileWriter file = new FileWriter("data.json")) {
      while (true) {
        Response response = client.newCall(request).execute();
        String json = null;
        if (response.body() != null) {
          json = response.body().string();
        }
        if (json == null || json.length() == 0 || json.contains("error_id") || json.contains(
            "\"has_more\": false")) {
          break;
        }
        file.write(json);
        currentPage++;
        url = String.format(baseUrl, currentPage);
        request = new Request.Builder().url(url).build();
      }

      System.out.println("Total questions: " + totalQuestions);
    }
  }

}
