package cse.java2.project.model;

import javax.persistence.Entity;
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
@Table(name = "comments")
public class Comment {

  @Id
  @Column(name = "comment_id")
  @SerializedName("comment_id")
  private int commentId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "owner_user_id")
  @SerializedName("owner")
  private Owner owner;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "reply_to_user_id")
  @SerializedName("reply_to_user")
  private Owner replyToUser;

  @Column(name = "edited")
  @SerializedName("edited")
  private boolean edited;

  @Column(name = "score")
  @SerializedName("score")
  private int score;

  @Column(name = "creation_date")
  @SerializedName("creation_date")
  private long creationDate;

  @Column(name = "post_id")
  @SerializedName("post_id")
  private int postId;

  @Column(name = "content_license")
  @SerializedName("content_license")
  private String contentLicense;

  @Column(name = "body", columnDefinition = "text")
  @SerializedName("body")
  private String body;

  // Constructors, getters, and setters

  public int getCommentId() {
    return commentId;
  }

  public void setCommentId(int commentId) {
    this.commentId = commentId;
  }

  public Owner getOwner() {
    return owner;
  }

  public void setOwner(Owner owner) {
    this.owner = owner;
  }

  public Owner getReplyToUser() {
    return replyToUser;
  }

  public void setReplyToUser(Owner replyToUser) {
    this.replyToUser = replyToUser;
  }

  public boolean isEdited() {
    return edited;
  }

  public void setEdited(boolean edited) {
    this.edited = edited;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public long getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(long creationDate) {
    this.creationDate = creationDate;
  }

  public int getPostId() {
    return postId;
  }

  public void setPostId(int postId) {
    this.postId = postId;
  }

  public String getContentLicense() {
    return contentLicense;
  }

  public void setContentLicense(String contentLicense) {
    this.contentLicense = contentLicense;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}
