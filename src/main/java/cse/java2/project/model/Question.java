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

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public Owner getOwner() {
    return owner;
  }

  public void setOwner(Owner owner) {
    this.owner = owner;
  }

  public boolean isAnswered() {
    return isAnswered;
  }

  public void setAnswered(boolean answered) {
    isAnswered = answered;
  }

  public int getViewCount() {
    return viewCount;
  }

  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }

  public int getAnswerCount() {
    return answerCount;
  }

  public void setAnswerCount(int answerCount) {
    this.answerCount = answerCount;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public long getLastActivityDate() {
    return lastActivityDate;
  }

  public void setLastActivityDate(long lastActivityDate) {
    this.lastActivityDate = lastActivityDate;
  }

  public long getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(long creationDate) {
    this.creationDate = creationDate;
  }

  public long getLastEditDate() {
    return lastEditDate;
  }

  public void setLastEditDate(long lastEditDate) {
    this.lastEditDate = lastEditDate;
  }

  public int getId() {
    return Id;
  }

  public void setId(int id) {
    Id = id;
  }

  public String getContentLicense() {
    return contentLicense;
  }

  public void setContentLicense(String contentLicense) {
    this.contentLicense = contentLicense;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
