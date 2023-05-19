package cse.java2.project.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.*;

@Entity
@Table(name = "questions")
public class Question {

  @Id
  @Column(name = "question_id")
  @SerializedName("question_id")
  private int id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "owner_user_id")
  @SerializedName("owner")
  private Owner owner;

  @Column(name = "is_answered")
  @SerializedName("is_answered")
  private boolean isAnswered;

  @Column(name = "view_count")
  @SerializedName("view_count")
  private int viewCount;

  @Column(name = "answer_count")
  @SerializedName("answer_count")
  private int answerCount;

  @Column(name = "score")
  @SerializedName("score")
  private int score;

  @Column(name = "last_activity_date")
  @SerializedName("last_activity_date")
  private long lastActivityDate;

  @Column(name = "creation_date")
  @SerializedName("creation_date")
  private long creationDate;

  @Column(name = "last_edit_date")
  @SerializedName("last_edit_date")
  private long lastEditDate;

  @Column(name = "content_license", columnDefinition = "text")
  @SerializedName("content_license")
  private String contentLicense;

  @Column(name = "link", columnDefinition = "text")
  @SerializedName("link")
  private String link;

  @Column(name = "title", columnDefinition = "text")
  @SerializedName("title")
  private String title;


  @ElementCollection
  @CollectionTable(name = "question_tags", joinColumns = @JoinColumn(name = "question_id"))
  @Column(name = "tag")
  @SerializedName("tags")
  private List<String> tags;

  // Constructors, getters, and setters

  // Convert tagNames to Tag objects after deserialization


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }
}

@Entity
@Table(name = "owner")
class Owner {

  @Id
  @Column(name = "user_id")
  @SerializedName("user_id")
  private int userId;

  @Column(name = "account_id")
  @SerializedName("account_id")
  private int accountId;

  @Column(name = "reputation")
  @SerializedName("reputation")
  private int reputation;

  @Column(name = "user_type")
  @SerializedName("user_type")
  private String userType;

  @Column(name = "profile_image")
  @SerializedName("profile_image")
  private String profileImage;

  @Column(name = "display_name")
  @SerializedName("display_name")
  private String displayName;

  @Column(name = "link")
  @SerializedName("link")
  private String link;

  // Constructors, getters, and setters
}

