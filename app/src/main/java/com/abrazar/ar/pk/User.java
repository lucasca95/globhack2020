package com.abrazar.ar.pk;

/**
 *
 */

public class User {
    private String UserName;
    private String UserEmail;
    private String UserTelephone;
    private String UserWebsite;
    private String UserAddress;
    private String UserBio;

    public User() {
    }

    public User(String userName, String userEmail, String userTelephone, String userWebsite, String userAddress, String userBio) {
        UserName = userName;
        UserEmail = userEmail;
        UserTelephone = userTelephone;
        UserWebsite = userWebsite;
        UserAddress = userAddress;
        UserBio = userBio;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserTelephone() {
        return UserTelephone;
    }

    public void setUserTelephone(String userTelephone) {
        UserTelephone = userTelephone;
    }

    public String getUserWebsite() {
        return UserWebsite;
    }

    public void setUserWebsite(String userWebsite) {
        UserWebsite = userWebsite;
    }

    public String getUserAddress() {
        return UserAddress;
    }

    public void setUserAddress(String userAddress) {
        UserAddress = userAddress;
    }

    public String getUserBio() {
        return UserBio;
    }

    public void setUserBio(String userBio) {
        UserBio = userBio;
    }
}
