package com.school.entity;

public class UserEntity {
    private String verifiedContent;
    private String avatarUrl;
    private Long userId;
    private String userName;
    private int followerCount;
    private boolean isFollow;
    //后续可能用一个对象存储
    private String userAuthInfo;
    private boolean isVerified;
    private String description;
}
