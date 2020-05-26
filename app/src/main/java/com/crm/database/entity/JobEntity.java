package com.crm.database.entity;

import com.crm.Utils.Constants;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = Constants.TABLE_JOB)
public class JobEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long jobNumber;
    @NonNull
    private String jobId;
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
    private String jobCategory;
    @NonNull
    private String jobDepartment;
    @NonNull
    private String emailId;

    public JobEntity(String jobId, @NonNull String jobTitle, @NonNull String jobDescription, @NonNull String jobPostedDate, @NonNull String jobLastDate, @NonNull String jobCountry, @NonNull String jobState, @NonNull String jobCity, @NonNull String jobCategory, @NonNull String jobDepartment, @NonNull String emailId) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.jobPostedDate = jobPostedDate;
        this.jobLastDate = jobLastDate;
        this.jobCountry = jobCountry;
        this.jobState = jobState;
        this.jobCity = jobCity;
        this.jobCategory = jobCategory;
        this.jobDepartment = jobDepartment;
        this.emailId = emailId;
    }

    public long getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(long jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
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
    public String getJobCategory() {
        return jobCategory;
    }

    public void setJobCategory(@NonNull String jobCategory) {
        this.jobCategory = jobCategory;
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
}
