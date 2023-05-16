package cse.java2.project.Data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cse.java2.project.model.Owner;
import cse.java2.project.model.Question;
import cse.java2.project.service.QuestionService;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.jetbrains.annotations.NotNull;

public class dealData {

  public static void main(String[] args) {
    try {
      String jsonStrings = Files.readString(Path.of("questions.json"));
      Type questionListType = new TypeToken<List<Question>>() {
      }.getType();
      List<Question> questions = new Gson().fromJson(jsonStrings, questionListType);
      //从json文件中获取从网上爬取的2500个问题，将其保存在本地

      Properties prop = loadDBUser();
      openDB(prop);
      clearDataInTable();
      for (Question question : questions) {
        importQuestion(question);
      }
      closeDB();

    } catch (IOException | SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static void clearDataInTable() {
    Statement stmt0;
    if (con != null) {
      try {
        stmt0 = con.createStatement();
        stmt0.executeUpdate("DROP TABLE IF EXISTS tag_question CASCADE;\n" +
            "DROP TABLE IF EXISTS tags CASCADE;\n" +
            "DROP TABLE IF EXISTS Questions CASCADE;\n" +
            "DROP TABLE IF EXISTS Owner CASCADE;\n");

        con.commit();
        String sql1 = "CREATE TABLE tags (\n"
            + "    tag_id SERIAL PRIMARY KEY,\n"
            + "    tag_name TEXT NOT NULL unique\n"
            + ");\n";
        stmt0.executeUpdate(sql1);
        con.commit();
        String sql3 = "CREATE TABLE Owner (\n"
            + "    user_id INT PRIMARY KEY,\n"
            + "    account_id INT NOT NULL,\n"
            + "    reputation INT NOT NULL,\n"
            + "    user_type TEXT NOT NULL,\n"
            + "    profile_image TEXT ,\n"
            + "    display_name TEXT NOT NULL,\n"
            + "    link TEXT \n"
            + ");\n";
        stmt0.executeUpdate(sql3);
        con.commit();
        String sql4 = "CREATE TABLE Questions (\n"
            + "    question_id INT PRIMARY KEY,\n"
            + "    owner_user_id INT NOT NULL,\n"
            + "    is_answered BOOLEAN NOT NULL,\n"
            + "    view_count INT NOT NULL,\n"
            + "    answer_count INT NOT NULL,\n"
            + "    score INT NOT NULL,\n"
            + "    last_activity_date timestamp NOT NULL,\n"
            + "    creation_date timestamp NOT NULL,\n"
            + "    last_edit_date timestamp DEFAULT NULL,\n"
            + "    content_license TEXT ,\n"
            + "    link TEXT NOT NULL,\n"
            + "    title TEXT NOT NULL,\n"
            + "    FOREIGN KEY (owner_user_id) REFERENCES Owner(user_id)\n"
            + ");\n";
        stmt0.executeUpdate(sql4);
        String sql2 = "CREATE TABLE tag_question (\n"
            + "    tag_question_id SERIAL PRIMARY KEY,\n"
            + "    tag_id INT NOT NULL,\n"
            + "    question_id INT NOT NULL,\n"
            + "    FOREIGN KEY (tag_id) REFERENCES tags(tag_id),\n"
            + "    FOREIGN KEY (question_id) REFERENCES Questions(question_id)\n"
            + ");\n";
        stmt0.executeUpdate(sql2);
        con.commit();
        con.commit();

        stmt0.close();
      } catch (SQLException ex) {
        throw new RuntimeException(ex);
      }
    }
  }

  private static void importQuestion(Question question) throws SQLException {
    if (question != null) {
      // Insert the owner into the Owner table and get the owner_id
      importOwner(question.getOwner());

      // Insert the tags into the Tags table and get the tag_ids
      List<Integer> tag_ids = new ArrayList<>();
      for (String tag_name : question.getTags()) {
        importTag(tag_name);
        int tag_id = getTagID(tag_name);
        if (tag_id != 0) {
          tag_ids.add(tag_id);
        }
      }

      // Insert the question into the Questions table
      String sql =
          "INSERT INTO Questions (question_id, owner_user_id, is_answered, view_count, answer_count, score, last_activity_date, creation_date, last_edit_date, content_license, link, title) "
              + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      PreparedStatement statement = con.prepareStatement(sql);
      statement.setInt(1, question.getId());
      statement.setInt(2, question.getOwner().getUserId());
      statement.setBoolean(3, question.isAnswered());
      statement.setInt(4, question.getViewCount());
      statement.setInt(5, question.getAnswerCount());
      statement.setInt(6, question.getScore());
      statement.setTimestamp(7, new Timestamp(question.getLastActivityDate() * 1000L));
      statement.setTimestamp(8, new Timestamp(question.getCreationDate() * 1000L));
      statement.setTimestamp(9,
          question.getLastEditDate() != 0 ? new Timestamp(question.getLastEditDate() * 1000L)
              : null);
      statement.setString(10, question.getContentLicense());
      statement.setString(11, question.getLink());
      statement.setString(12, question.getTitle());
      statement.executeUpdate();

      // Insert the tag_question relationships into the Tag_Question table
      for (int tag_id : tag_ids) {
        String tag_question_sql = "INSERT INTO tag_question (tag_id, question_id) VALUES (?, ?)";
        PreparedStatement tag_question_statement = con.prepareStatement(tag_question_sql);
        tag_question_statement.setInt(1, tag_id);
        tag_question_statement.setInt(2, question.getId());
        tag_question_statement.executeUpdate();
      }

      con.commit();
      statement.close();
    }
  }

  private static void importOwner(Owner owner) throws SQLException {
    String sql =
        "INSERT INTO Owner (user_id, account_id, reputation, user_type, profile_image, display_name, link) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?) ON CONFLICT (user_id) DO NOTHING ";

    PreparedStatement statement = con.prepareStatement(sql);
    statement.setInt(1, owner.getUserId());
    statement.setInt(2, owner.getAccountId());
    statement.setInt(3, owner.getReputation());
    statement.setString(4, owner.getUserType());
    statement.setString(5, owner.getProfileImage());
    statement.setString(6, owner.getDisplayName());
    statement.setString(7, owner.getLink());
    statement.executeUpdate();
    con.commit();
    statement.close();

  }


  private static void importTag(String tag_name) throws SQLException {
    String sql = "INSERT INTO tags (tag_name) VALUES (?) ON CONFLICT (tag_name) DO NOTHING";
    PreparedStatement statement = con.prepareStatement(sql);
    statement.setString(1, tag_name);
    statement.executeUpdate();
    con.commit();
    statement.close();
  }

  private static HashMap<String, Integer> TagToID = new HashMap<>();

  private static Integer getTagID(String Tag) throws SQLException {
    if (TagToID.containsKey(Tag)) {
      return TagToID.get(Tag);
    }
    String sql = "SELECT tag_id FROM tags WHERE tag_name = ?";
    PreparedStatement statement = con.prepareStatement(sql);
    statement.setString(1, Tag);
    ResultSet rs = statement.executeQuery();
    int TagId = 0;
    if (rs.next()) {
      TagId = rs.getInt("tag_id");
      if (!TagToID.containsKey(Tag)) {
        TagToID.put(Tag, TagId);
      }
    }
    rs.close();
    statement.close();
    return TagId;
  }


  private static Properties loadDBUser() {
    Properties properties = new Properties();
    try {
      properties.load(
          new InputStreamReader(new FileInputStream(
              "D:\\IDEA_Proj\\Java2_proj\\src\\main\\resources\\dbUser.properties")));
      return properties;
    } catch (IOException e) {
      System.err.println("can not find db user file");
      throw new RuntimeException(e);
    }
  }

  private static Connection con = null;
  private static PreparedStatement stmt = null;

  private static void openDB(Properties prop) {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (Exception e) {
      System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
      System.exit(1);
    }
    String url =
        "jdbc:postgresql://" + prop.getProperty("host") + ":" + prop.getProperty("port")
            + "/" + prop.getProperty("database");
    try {
      con = DriverManager.getConnection(url, prop);
      if (con != null) {
        System.out.println("Successfully connected to the database "
            + prop.getProperty("database") + " as " + prop.getProperty("user"));
        con.setAutoCommit(false);
      }
    } catch (SQLException e) {
      System.err.println("Database connection failed");
      System.err.println(e.getMessage());
      System.exit(1);
    }
  }

  private static void closeDB() {
    if (con != null) {
      try {
        if (stmt != null) {
          stmt.close();
        }
        con.close();
        con = null;
      } catch (Exception ignored) {
      }
    }
  }


}
