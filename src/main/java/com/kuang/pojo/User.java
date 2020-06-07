package com.kuang.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;

import javax.validation.constraints.Email;
import java.sql.Timestamp;

/**
 * @author HP
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class User {

    /** 用户id **/
    private int userId;
    /** 用户qq号码 **/
    private String userQqNumber;
    /** 用户名 **/
    private String userName;
    /** 用户密码 **/
    private String userPassword;
    /** 用户邮箱 **/
    private String userEmail;
    /** 用户头像 **/
    private byte[] userProfilePhoto;
    /** 用户注册时间 **/
    private Timestamp userRegistrationTime;
    /** 用户生日 **/
    private Timestamp userBirthday;
    /** 用户电话号码 **/
    private String userTelephoneNumber;
    /** 用户昵称 **/
    private String userNickName;
    /** 用户个人签名 **/
    private String personalSignature;
    /** 用户人脸识别uuid **/
    private String userUuid;

    public User(int i, String userQqNumber, String userName, String userPassword, String userEmail, byte[] headData, Timestamp userRegistrationTime, Timestamp userBirthday, String userTelephoneNumber, String userNickName, String userSignature) {

    }

    public User() {

    }

    public User setUuid(String uuid) {
        this.userUuid = uuid;
        return this;
    }

    public User setUser_nickname(String userNickName) {
        this.userNickName = userNickName;
        return this;
    }

    public User setUser_name(String userName) {
        this.userName = userName;
        return this;
    }

    public User setUser_password(String userPassword) {
        this.userPassword = userPassword;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public User setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getUserQqNumber() {
        return userQqNumber;
    }

    public User setUserQqNumber(String userQqNumber) {
        this.userQqNumber = userQqNumber;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public User setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public User setUserPassword(String userPassword) {
        this.userPassword = userPassword;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public User setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public byte[] getUserProfilePhoto() {
        return userProfilePhoto;
    }

    public User setUserProfilePhoto(byte[] userProfilePhoto) {
        this.userProfilePhoto = userProfilePhoto;
        return this;
    }

    public Timestamp getUserRegistrationTime() {
        return userRegistrationTime;
    }

    public User setUserRegistrationTime(Timestamp userRegistrationTime) {
        this.userRegistrationTime = userRegistrationTime;
        return this;
    }

    public Timestamp getUserBirthday() {
        return userBirthday;
    }

    public User setUserBirthday(Timestamp userBirthday) {
        this.userBirthday = userBirthday;
        return this;
    }

    public String getUserTelephoneNumber() {
        return userTelephoneNumber;
    }

    public User setUserTelephoneNumber(String userTelephoneNumber) {
        this.userTelephoneNumber = userTelephoneNumber;
        return this;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public User setUserNickName(String userNickName) {
        this.userNickName = userNickName;
        return this;
    }

    public String getPersonalSignature() {
        return personalSignature;
    }

    public User setPersonalSignature(String personalSignature) {
        this.personalSignature = personalSignature;
        return this;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public User setUserUuid(String userUuid) {
        this.userUuid = userUuid;
        return this;
    }



}
