package cse.java2.project.service;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cse.java2.project.model.Answer;
import cse.java2.project.model.Comment;
import cse.java2.project.model.Owner;
import cse.java2.project.model.Question;
import cse.java2.project.repository.AnswerRepository;
import cse.java2.project.repository.CommentRepository;
import cse.java2.project.repository.QuestionRepository;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
@ComponentScan("cse.java2.project.repository") // 包路径
public class QuestionAndAnswerService {

  private final QuestionRepository questionRepository;
  private final AnswerRepository answerRepository;
  private final CommentRepository commentRepository;

  @Autowired
  public QuestionAndAnswerService(QuestionRepository questionRepository,
      AnswerRepository answerRepository, CommentRepository commentRepository) {
    this.questionRepository = questionRepository;
    this.answerRepository = answerRepository;
    this.commentRepository = commentRepository;

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

      String jsonStrings3 = Files.readString(Path.of("comments.json"));
      Type commentListType = new TypeToken<List<Comment>>() {
      }.getType();
      List<Comment> comments = new Gson().fromJson(jsonStrings3, commentListType);
      commentRepository.saveAll(comments);

      String jsonStrings4 = Files.readString(Path.of("AnswerComments.json"));
      Type commentListType2 = new TypeToken<List<Comment>>() {
      }.getType();
      List<Comment> comments2 = new Gson().fromJson(jsonStrings4, commentListType2);
      commentRepository.saveAll(comments2);
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

  public long getPercentageOfAnswerUpvote() {
    return answerRepository.countQuestionsWithMoreUpvote();
  }

  public long getQuestionCount() {
    return questionRepository.count();
  }

  public List<Map<String, Object>> getTopTenTagsWithCount() {
    List<Object[]> tagCounts = questionRepository.getTagCount();

    // 对标签出现次数进行排序（按降序）
    tagCounts.sort((a, b) -> ((Long) b[1]).compareTo((Long) a[1]));

    // 获取前十名标签及其出现次数
    List<Map<String, Object>> topTenTagsWithCount = tagCounts.stream()
        .limit(10)
        .map(tagCount -> {
          Map<String, Object> tagData = new HashMap<>();
          tagData.put("tag", tagCount[0]);
          tagData.put("count", tagCount[1]);
          return tagData;
        })
        .collect(Collectors.toList());

    return topTenTagsWithCount;
  }


  public List<Object[]> getTopTenTagsWithUpvote() {
    List<Question> questions = questionRepository.findAll();
    HashMap<String, Integer> map = new HashMap<>();

    for (Question question : questions) {
      List<String> tags = questionRepository.getTagsByQuestionId(question.getId());
      tags.sort(String::compareTo);
      String tag = tags.toString();
      System.out.println(tag);

      map.put(tag, map.getOrDefault(tag, 0) + question.getUpVoteCount());
    }

    List<Object[]> topTags = map.entrySet()
        .stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .limit(10)
        .map(entry -> new Object[]{entry.getKey(), entry.getValue()})
        .collect(Collectors.toList());

    return topTags;
  }

  public List<Object[]> getTopTenTagsWithView() {
    List<Question> questions = questionRepository.findAll();
    HashMap<String, Integer> map = new HashMap<>();

    for (Question question : questions) {
      List<String> tags = questionRepository.getTagsByQuestionId(question.getId());
      tags.sort(String::compareTo);
      String tag = tags.toString();
      System.out.println(tag);

      map.put(tag, map.getOrDefault(tag, 0) + question.getViewCount());
    }

    List<Object[]> topTags = map.entrySet()
        .stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .limit(10)
        .map(entry -> new Object[]{entry.getKey(), entry.getValue()})
        .collect(Collectors.toList());

    return topTags;
  }

  public HashMap<String, Long> userTime() {
    HashMap<String, Long> userTime = new HashMap<>();
    List<Question> questions = questionRepository.findAll();
    List<Answer> answers = answerRepository.findAll();
    for (Question q : questions) {
      Owner owner = q.getOwner();
      String s = owner.getDisplayName() + "(" + owner.getUserId() + ")";
      userTime.put(s, userTime.getOrDefault(s, 0L) + 1);
    }
    for (Answer a : answers) {
      Owner owner = a.getOwner();
      String s = owner.getDisplayName() + "(" + owner.getUserId() + ")";
      userTime.put(s, userTime.getOrDefault(s, 0L) + 1);
    }

    List<Comment> comments = commentRepository.findAll();
    for (Comment c : comments) {
      Owner owner = c.getOwner();
      String s = owner.getDisplayName() + "(" + owner.getUserId() + ")";
      userTime.put(s, userTime.getOrDefault(s, 0L) + 1);
    }

    return userTime;
  }

  public HashMap<String, Long> userDistribution() {
    HashMap<String, Long> userTIme = this.userTime();
    HashMap<String, Long> userDistribution = new HashMap<>();
    for (long v : userTIme.values()) {
      String s = getUserRange(v);
      userDistribution.put(s, userDistribution.getOrDefault(s, 0L) + 1);
    }
    return userDistribution;
  }

  public List<Object[]> TopUsers() {
    HashMap<String, Long> userTime = this.userTime();

    // 使用HashMap的entrySet创建流，然后对流中的元素按照value进行排序
    List<Object[]> topUsers = getTopUsers(userTime);

    return topUsers;
  }

  public List<Object[]> TopUsersWithMostAnswers() {

    HashMap<String, Long> userTime = new HashMap<>();

    List<Answer> answers = answerRepository.findAll();

    for (Answer a : answers) {
      Owner owner = a.getOwner();
      String s = owner.getDisplayName() + "(" + owner.getUserId() + ")";
      userTime.put(s, userTime.getOrDefault(s, 0L) + 1);
    }

    // 使用HashMap的entrySet创建流，然后对流中的元素按照value进行排序
    List<Object[]> topUsers = getTopUsers(userTime);

    return topUsers;
  }

  public List<Object[]> TopUsersWithMostComments() {
    HashMap<String, Long> userTime = new HashMap<>();
    List<Comment> comments = commentRepository.findAll();
    for (Comment c : comments) {
      Owner owner = c.getOwner();
      String s = owner.getDisplayName() + "(" + owner.getUserId() + ")";
      userTime.put(s, userTime.getOrDefault(s, 0L) + 1);
    }

    List<Object[]> topUsers = getTopUsers(userTime);

    return topUsers;
  }

  @NotNull
  private static List<Object[]> getTopUsers(HashMap<String, Long> userTime) {
    // 使用HashMap的entrySet创建流，然后对流中的元素按照value进行排序
    List<Map.Entry<String, Long>> sortedEntries = userTime.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .toList();

    // 获取前十名的entry对象
    List<Map.Entry<String, Long>> topEntries = sortedEntries.stream()
        .limit(10)
        .toList();

    // 转换为List<Object[]>形式返回
    List<Object[]> topUsers = topEntries.stream()
        .map(entry -> new Object[]{entry.getKey(), entry.getValue()})
        .collect(Collectors.toList());
    return topUsers;
  }

  private String getUserRange(long postTime) {
    if (postTime <= 1) {
      return "#1: only 1 post";
    } else if (postTime <= 2) {
      return "#2: only 2 post";
    } else if (postTime <= 5) {
      return "#3: 3-5 posts";
    } else if (postTime <= 10) {
      return "#4: 6-10 posts";
    } else if (postTime <= 30) {
      return "#5: 11-30 posts";
    } else {
      return "#6 more than 30 posts";
    }
  }


  public List<Answer> getAnswersBy(Boolean isAccepted, Integer questionId) {
    // 根据需要构建查询条件
    return answerRepository.getFilteredAnswers(isAccepted, questionId);
  }

  public List<Question> getFilteredQuestions(Boolean isAnswered, Integer id) {
    return questionRepository.getFilteredQuestions(isAnswered, id);
  }


}
