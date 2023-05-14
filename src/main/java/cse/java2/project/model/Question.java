package cse.java2.project.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Question {

  @SerializedName("tags")
  private List<String> tags;
  @SerializedName("owner")
  private Owner owner;
  @SerializedName("is_answered")
  private boolean isAnswered;
  @SerializedName("view_count")
  private int viewCount;
  @SerializedName("answer_count")
  private int answerCount;
  @SerializedName("score")
  private int score;
  @SerializedName("last_activity_date")
  private long lastActivityDate;
  @SerializedName("creation_date")
  private long creationDate;
  @SerializedName("last_edit_date")
  private long lastEditDate;
  @SerializedName("question_id")
  private int Id;
  @SerializedName("content_license")
  private String contentLicense;
  @SerializedName("link")
  private String link;
  @SerializedName("title")
  private String title;

  public Question(List<String> tags, Owner owner, boolean isAnswered, int viewCount,
      int answerCount, int score, long lastActivityDate, long creationDate, long lastEditDate,
      int questionId, String contentLicense, String link, String title) {
    this.tags = tags;
    this.owner = owner;
    this.isAnswered = isAnswered;
    this.viewCount = viewCount;
    this.answerCount = answerCount;
    this.score = score;
    this.lastActivityDate = lastActivityDate;
    this.creationDate = creationDate;
    this.lastEditDate = lastEditDate;
    this.Id = questionId;
    this.contentLicense = contentLicense;
    this.link = link;
    this.title = title;
  }

  static class Owner {

    @SerializedName("account_id")
    private int accountId;
    @SerializedName("reputation")
    private int reputation;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("user_type")
    private String userType;
    @SerializedName("profile_image")
    private String profileImage;
    @SerializedName("display_name")
    private String displayName;
    @SerializedName("link")
    private String link;

    public Owner(int accountId, int reputation, int userId, String userType, String profileImage,
        String displayName, String link) {
      this.accountId = accountId;
      this.reputation = reputation;
      this.userId = userId;
      this.userType = userType;
      this.profileImage = profileImage;
      this.displayName = displayName;
      this.link = link;
    }
  }
}
