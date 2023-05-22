package cse.java2.project.model;

import com.google.gson.annotations.SerializedName;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "owner")
public class Owner {

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

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getAccountId() {
    return accountId;
  }

  public void setAccountId(int accountId) {
    this.accountId = accountId;
  }

  public int getReputation() {
    return reputation;
  }

  public void setReputation(int reputation) {
    this.reputation = reputation;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  public String getProfileImage() {
    return profileImage;
  }

  public void setProfileImage(String profileImage) {
    this.profileImage = profileImage;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }
}
