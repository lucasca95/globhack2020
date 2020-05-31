package com.abrazar.ar.pk;

/**
 *
 */

public class Review {
    private String reviewingUser;
    private String reviewedUser;
    private String reviewedUserName;
    private String review;

    public Review() {
    }

    public Review(String reviewingUser, String reviewedUser, String reviewedUserName, String review) {
        this.reviewingUser = reviewingUser;
        this.reviewedUser = reviewedUser;
        this.reviewedUserName = reviewedUserName;
        this.review = review;
    }

    public String getReviewingUser() {
        return reviewingUser;
    }

    public void setReviewingUser(String reviewingUser) {
        this.reviewingUser = reviewingUser;
    }

    public String getReviewedUser() {
        return reviewedUser;
    }

    public void setReviewedUser(String reviewedUser) {
        this.reviewedUser = reviewedUser;
    }

    public String getReviewedUserName() {
        return reviewedUserName;
    }

    public void setReviewedUserName(String reviewedUserName) {
        this.reviewedUserName = reviewedUserName;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
