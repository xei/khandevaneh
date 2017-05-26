package ir.tvnasim.khandevaneh.account;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hamidreza on 5/3/17.
 */

public class UserInfo {

    @SerializedName("id")
    private String id;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("mobile")
    private String phoneNo;

    @SerializedName("email")
    private String emailAddress;

    @SerializedName("address")
    private String postalAddress;

    @SerializedName("credit")
    private int melonScore;

    @SerializedName("exLevel")
    private int experienceScore;

    @SerializedName("profilePic")
    private String avatar;

    @SerializedName("registerDate")
    private String registrationDate;

    @SerializedName("registrationRequest")
    private String registrationRequest;

    @SerializedName("isInClub")
    private String isInClub;

    @SerializedName("profileComplete")
    private String profileIsCompleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public int getMelonScore() {
        return melonScore;
    }

    public void setMelonScore(int melonScore) {
        this.melonScore = melonScore;
    }

    public int getExperienceScore() {
        return experienceScore;
    }

    public void setExperienceScore(int experienceScore) {
        this.experienceScore = experienceScore;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getRegistrationRequest() {
        return registrationRequest;
    }

    public void setRegistrationRequest(String registrationRequest) {
        this.registrationRequest = registrationRequest;
    }

    public String getIsInClub() {
        return isInClub;
    }

    public void setIsInClub(String isInClub) {
        this.isInClub = isInClub;
    }

    public String getProfileIsCompleted() {
        return profileIsCompleted;
    }

    public void setProfileIsCompleted(String profileIsCompleted) {
        this.profileIsCompleted = profileIsCompleted;
    }

    public boolean isProfileCompleted() {
        return !profileIsCompleted.equals("0");
    }
}
