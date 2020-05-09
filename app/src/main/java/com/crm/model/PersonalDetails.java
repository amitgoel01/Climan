package com.crm.model;

public class PersonalDetails {

    private static PersonalDetails sInstance;
    private String emailId;
    private String jobTitle;
    private String empId;

    private PersonalDetails() {

    }

    public static PersonalDetails getInstance() {
        if (sInstance == null) {
            synchronized (PersonalDetails.class) {
                if (sInstance == null) {
                    sInstance = new PersonalDetails();
                }
            }
        }
        return sInstance;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
}
