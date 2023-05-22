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
@Table(name = "answers")
public class Answer {

  @Id
  @Column(name = "answer_id")
  @SerializedName("answer_id")
  private int id;

  @Column(name = "body", columnDefinition = "text")
  @SerializedName("body")
  private String body;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "owner_user_id")
  @SerializedName("owner")
  private Owner owner;

  @Column(name = "is_accepted")
  @SerializedName("is_accepted")
  private boolean isAccepted;

  @Column(name = "score")
  @SerializedName("score")
  private int score;

  @Column(name = "comment_count")
  @SerializedName("comment_count")
  private int commentCount;

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

  @Column(name = "question_id")
  @SerializedName("question_id")
  private int questionId;

  @ElementCollection
  @CollectionTable(name = "answer_tags", joinColumns = @JoinColumn(name = "answer_id"))
  @Column(name = "tag")
  @SerializedName("tags")
  private List<String> tags;

  @Column(name = "up_vote_count")
  @SerializedName("up_vote_count")
  private int upVoteCount;
  // Constructors, getters, and setters

  // Convert ownerId to Owner object after deserialization

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public int getCommentCount() {
    return commentCount;
  }

  public void setCommentCount(int commentCount) {
    this.commentCount = commentCount;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public int getUpVoteCount() {
    return upVoteCount;
  }

  public void setUpVoteCount(int upVoteCount) {
    this.upVoteCount = upVoteCount;
  }

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

  public boolean isAccepted() {
    return isAccepted;
  }

  public void setAccepted(boolean accepted) {
    isAccepted = accepted;
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

  public int getQuestionId() {
    return questionId;
  }

  public void setQuestionId(int questionId) {
    this.questionId = questionId;
  }
}

