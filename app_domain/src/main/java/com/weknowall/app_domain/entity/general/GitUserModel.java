package com.weknowall.app_domain.entity.general;

/**
 * User: laomao
 * Date: 2017-03-01
 * Time: 18-00
 */

public class GitUserModel {
    private String name;
    private int id;
    private String avatar;
    private String userUrl;
    private String userHtmlUrl;
    private String followersUrl;
    private String followingUrl;

    public GitUserModel() {
    }

    public GitUserModel(String name, int id, String avatar, String userUrl, String userHtmlUrl, String followersUrl, String followingUrl) {
        this.name = name;
        this.id = id;
        this.avatar = avatar;
        this.userUrl = userUrl;
        this.userHtmlUrl = userHtmlUrl;
        this.followersUrl = followersUrl;
        this.followingUrl = followingUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public String getUserHtmlUrl() {
        return userHtmlUrl;
    }

    public void setUserHtmlUrl(String userHtmlUrl) {
        this.userHtmlUrl = userHtmlUrl;
    }

    public String getFollowersUrl() {
        return followersUrl;
    }

    public void setFollowersUrl(String followersUrl) {
        this.followersUrl = followersUrl;
    }

    public String getFollowingUrl() {
        return followingUrl;
    }

    public void setFollowingUrl(String followingUrl) {
        this.followingUrl = followingUrl;
    }
}
