package cse.java2.project.model;

import com.google.gson.annotations.SerializedName;

public class Owner {

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

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
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