package com.crm.database.entity;

import com.crm.Utils.Constants;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Constants.TABLE_CLIENT_PERSON)
public class ClientPersonEntity {
    @PrimaryKey(autoGenerate = true)
    private long cpId; // auto

    private long clientId;

    private String clientPersonId;

    @NonNull
    private String cpName;

    @NonNull
    private String cpDesignation;

    @NonNull
    private String cpEmailId;

    @NonNull
    private String cpMobNo;

    @NonNull
    private String cpPhNo;



    @NonNull
    private String doc; //date of contact

    @NonNull
    private String cpStatus; // interested or not interested

    @NonNull
    private String cpComments;



    @NonNull
    private String nextContactPerson;

    @NonNull
    private String nextClientContactPerson;

    @NonNull
    private String nextClientDesignation;

    @NonNull
    private String nextClientEmailId;

   @NonNull
    private String nextClientMobNo;

    @NonNull
    private String nextClientPhNo;

    @NonNull
    private String nextMeetingDate;

    @NonNull
    private String nextMeetingTime;

    @NonNull
    private String serviceRequired; // a,b,c,d,e

    @NonNull
    private String surveyDone;

    @NonNull
    private String surveyDate;

    @NonNull
    private String meetingOutcome;

    public ClientPersonEntity(String clientPersonId, @NonNull String cpName, @NonNull String cpDesignation, @NonNull String cpEmailId, @NonNull String cpMobNo, @NonNull String cpPhNo, @NonNull String doc, @NonNull String cpStatus, @NonNull String cpComments, @NonNull String nextContactPerson, @NonNull String nextClientContactPerson, @NonNull String nextClientDesignation, @NonNull String nextClientEmailId, @NonNull String nextClientMobNo, @NonNull String nextClientPhNo, @NonNull String nextMeetingDate, @NonNull String nextMeetingTime, @NonNull String serviceRequired, @NonNull String surveyDone, @NonNull String surveyDate, @NonNull String meetingOutcome) {
        this.clientPersonId = clientPersonId;
        this.cpName = cpName;
        this.cpDesignation = cpDesignation;
        this.cpEmailId = cpEmailId;
        this.cpMobNo = cpMobNo;
        this.cpPhNo = cpPhNo;
        this.doc = doc;
        this.cpStatus = cpStatus;
        this.cpComments = cpComments;
        this.nextContactPerson = nextContactPerson;
        this.nextClientContactPerson = nextClientContactPerson;
        this.nextClientDesignation = nextClientDesignation;
        this.nextClientEmailId = nextClientEmailId;
        this.nextClientMobNo = nextClientMobNo;
        this.nextClientPhNo = nextClientPhNo;
        this.nextMeetingDate = nextMeetingDate;
        this.nextMeetingTime = nextMeetingTime;
        this.serviceRequired = serviceRequired;
        this.surveyDone = surveyDone;
        this.surveyDate = surveyDate;
        this.meetingOutcome = meetingOutcome;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getCpId() {
        return cpId;
    }

    public void setCpId(long cpId) {
        this.cpId = cpId;
    }

    public String getClientPersonId() {
        return clientPersonId;
    }

    public void setClientPersonId(String clientPersonId) {
        this.clientPersonId = clientPersonId;
    }

    @NonNull
    public String getCpName() {
        return cpName;
    }

    public void setCpName(@NonNull String cpName) {
        this.cpName = cpName;
    }

    @NonNull
    public String getCpDesignation() {
        return cpDesignation;
    }

    public void setCpDesignation(@NonNull String cpDesignation) {
        this.cpDesignation = cpDesignation;
    }

    @NonNull
    public String getCpEmailId() {
        return cpEmailId;
    }

    public void setCpEmailId(@NonNull String cpEmailId) {
        this.cpEmailId = cpEmailId;
    }

    @NonNull
    public String getCpMobNo() {
        return cpMobNo;
    }

    public void setCpMobNo(@NonNull String cpMobNo) {
        this.cpMobNo = cpMobNo;
    }

    @NonNull
    public String getCpPhNo() {
        return cpPhNo;
    }

    public void setCpPhNo(@NonNull String cpPhNo) {
        this.cpPhNo = cpPhNo;
    }

    @NonNull
    public String getDoc() {
        return doc;
    }

    public void setDoc(@NonNull String doc) {
        this.doc = doc;
    }

    @NonNull
    public String getCpStatus() {
        return cpStatus;
    }

    public void setCpStatus(@NonNull String cpStatus) {
        this.cpStatus = cpStatus;
    }

    @NonNull
    public String getCpComments() {
        return cpComments;
    }

    public void setCpComments(@NonNull String cpComments) {
        this.cpComments = cpComments;
    }

    @NonNull
    public String getNextContactPerson() {
        return nextContactPerson;
    }

    public void setNextContactPerson(@NonNull String nextContactPerson) {
        this.nextContactPerson = nextContactPerson;
    }

    @NonNull
    public String getNextClientContactPerson() {
        return nextClientContactPerson;
    }

    public void setNextClientContactPerson(@NonNull String nextClientContactPerson) {
        this.nextClientContactPerson = nextClientContactPerson;
    }

    @NonNull
    public String getNextClientDesignation() {
        return nextClientDesignation;
    }

    public void setNextClientDesignation(@NonNull String nextClientDesignation) {
        this.nextClientDesignation = nextClientDesignation;
    }

    @NonNull
    public String getNextClientEmailId() {
        return nextClientEmailId;
    }

    public void setNextClientEmailId(@NonNull String nextClientEmailId) {
        this.nextClientEmailId = nextClientEmailId;
    }

    @NonNull
    public String getNextMeetingDate() {
        return nextMeetingDate;
    }

    public void setNextMeetingDate(@NonNull String nextMeetingDate) {
        this.nextMeetingDate = nextMeetingDate;
    }

    @NonNull
    public String getNextMeetingTime() {
        return nextMeetingTime;
    }

    public void setNextMeetingTime(@NonNull String nextMeetingTime) {
        this.nextMeetingTime = nextMeetingTime;
    }

    @NonNull
    public String getNextClientMobNo() {
        return nextClientMobNo;
    }

    public void setNextClientMobNo(@NonNull String nextClientMobNo) {
        this.nextClientMobNo = nextClientMobNo;
    }

    @NonNull
    public String getNextClientPhNo() {
        return nextClientPhNo;
    }

    public void setNextClientPhNo(@NonNull String nextClientPhNo) {
        this.nextClientPhNo = nextClientPhNo;
    }

    @NonNull
    public String getServiceRequired() {
        return serviceRequired;
    }

    public void setServiceRequired(@NonNull String serviceRequired) {
        this.serviceRequired = serviceRequired;
    }

    @NonNull
    public String getSurveyDone() {
        return surveyDone;
    }

    public void setSurveyDone(@NonNull String surveyDone) {
        this.surveyDone = surveyDone;
    }

    @NonNull
    public String getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(@NonNull String surveyDate) {
        this.surveyDate = surveyDate;
    }

    @NonNull
    public String getMeetingOutcome() {
        return meetingOutcome;
    }

    public void setMeetingOutcome(@NonNull String meetingOutcome) {
        this.meetingOutcome = meetingOutcome;
    }
}
