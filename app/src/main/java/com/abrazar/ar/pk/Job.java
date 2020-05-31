package com.abrazar.ar.pk;

/**
 * Data class
 */

public class Job {
    private String jobName;
    private String jobDesc;
    private String jobBudget;
    private String jobLocationName;
    private String jobPostedDate;
    private String jobPostedUserId;
    private String jobPostedUserName;
    private String jobStatus;
    private String jobKeyWord;
    private Double jobLocationLong;
    private Double jobLocationLat;

    public Job() {
    }

    public Job(String jobName, String jobDesc, String jobBudget, String jobLocationName, String jobPostedDate, String jobPostedUserId, String jobPostedUserName, String jobStatus, String jobKeyWord, Double jobLocationLong, Double jobLocationLat) {
        this.jobName = jobName;
        this.jobDesc = jobDesc;
        this.jobBudget = jobBudget;
        this.jobLocationName = jobLocationName;
        this.jobPostedDate = jobPostedDate;
        this.jobPostedUserId = jobPostedUserId;
        this.jobPostedUserName = jobPostedUserName;
        this.jobStatus = jobStatus;
        this.jobKeyWord = jobKeyWord;
        this.jobLocationLong = jobLocationLong;
        this.jobLocationLat = jobLocationLat;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public String getJobBudget() {
        return jobBudget;
    }

    public void setJobBudget(String jobBudget) {
        this.jobBudget = jobBudget;
    }

    public String getJobLocationName() {
        return jobLocationName;
    }

    public void setJobLocationName(String jobLocationName) {
        this.jobLocationName = jobLocationName;
    }

    public String getJobPostedDate() {
        return jobPostedDate;
    }

    public void setJobPostedDate(String jobPostedDate) {
        this.jobPostedDate = jobPostedDate;
    }

    public String getJobPostedUserId() {
        return jobPostedUserId;
    }

    public void setJobPostedUserId(String jobPostedUserId) {
        this.jobPostedUserId = jobPostedUserId;
    }

    public String getJobPostedUserName() {
        return jobPostedUserName;
    }

    public void setJobPostedUserName(String jobPostedUserName) {
        this.jobPostedUserName = jobPostedUserName;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobKeyWord() {
        return jobKeyWord;
    }

    public void setJobKeyWord(String jobKeyWord) {
        this.jobKeyWord = jobKeyWord;
    }

    public Double getJobLocationLong() {
        return jobLocationLong;
    }

    public void setJobLocationLong(Double jobLocationLong) {
        this.jobLocationLong = jobLocationLong;
    }

    public Double getJobLocationLat() {
        return jobLocationLat;
    }

    public void setJobLocationLat(Double jobLocationLat) {
        this.jobLocationLat = jobLocationLat;
    }
}
