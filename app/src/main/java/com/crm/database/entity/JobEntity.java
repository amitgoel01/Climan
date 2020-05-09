package com.crm.database.entity;

import com.crm.Utils.Constants;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = Constants.TABLE_JOB)
public class JobEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long jobId;
    @NonNull
    private String jobTitle;
    @NonNull
    private String jobDescription;
    @NonNull
    private String jobPostedDate;
    @NonNull
    private String jobLastDate;
    @NonNull
    private String jobCountry;
    @NonNull
    private String jobState;
    @NonNull
    private String jobCity;
    @NonNull
    private String jobcatagory;
    @NonNull
    private String jobDepartment;
    @NonNull
    private String emailId;

    public JobEntity() {

    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    @NonNull
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(@NonNull String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @NonNull
    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(@NonNull String jobDescription) {
        this.jobDescription = jobDescription;
    }

    @NonNull
    public String getJobPostedDate() {
        return jobPostedDate;
    }

    public void setJobPostedDate(@NonNull String jobPostedDate) {
        this.jobPostedDate = jobPostedDate;
    }

    @NonNull
    public String getJobLastDate() {
        return jobLastDate;
    }

    public void setJobLastDate(@NonNull String jobLastDate) {
        this.jobLastDate = jobLastDate;
    }

    @NonNull
    public String getJobCountry() {
        return jobCountry;
    }

    public void setJobCountry(@NonNull String jobCountry) {
        this.jobCountry = jobCountry;
    }

    @NonNull
    public String getJobState() {
        return jobState;
    }

    public void setJobState(@NonNull String jobState) {
        this.jobState = jobState;
    }

    @NonNull
    public String getJobCity() {
        return jobCity;
    }

    public void setJobCity(@NonNull String jobCity) {
        this.jobCity = jobCity;
    }

    @NonNull
    public String getJobcatagory() {
        return jobcatagory;
    }

    public void setJobcatagory(@NonNull String jobcatagory) {
        this.jobcatagory = jobcatagory;
    }

    @NonNull
    public String getJobDepartment() {
        return jobDepartment;
    }

    public void setJobDepartment(@NonNull String jobDepartment) {
        this.jobDepartment = jobDepartment;
    }

    @NonNull
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(@NonNull String emailId) {
        this.emailId = emailId;
    }

    @Override
    public String toString() {
        return "JobEntity{" +
                "jobId=" + jobId +
                ", jobTitle='" + jobTitle + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", jobPostedDate='" + jobPostedDate + '\'' +
                ", jobLastDate='" + jobLastDate + '\'' +
                ", jobCountry='" + jobCountry + '\'' +
                ", jobState='" + jobState + '\'' +
                ", jobCity='" + jobCity + '\'' +
                ", jobType='" + jobcatagory + '\'' +
                ", jobDepartment='" + jobDepartment + '\'' +
                ", emailId='" + emailId + '\'' +
                '}';
    }
}
