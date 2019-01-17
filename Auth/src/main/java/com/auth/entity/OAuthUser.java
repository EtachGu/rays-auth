package com.auth.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "oauth_user")
public class OAuthUser {
    @Id
    @Column(name = "user_name")
    private String userName;

    private String password;

    @Column(name = "nick_name")
    private String nickName;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "failure_count")
    private Boolean failureCount;

    @Column(name = "failure_time")
    private Date failureTime;

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return nick_name
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * @param nickName
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * @return avatar_url
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * @param avatarUrl
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     * @return failure_count
     */
    public Boolean getFailureCount() {
        return failureCount;
    }

    /**
     * @param failureCount
     */
    public void setFailureCount(Boolean failureCount) {
        this.failureCount = failureCount;
    }

    /**
     * @return failure_time
     */
    public Date getFailureTime() {
        return failureTime;
    }

    /**
     * @param failureTime
     */
    public void setFailureTime(Date failureTime) {
        this.failureTime = failureTime;
    }
}