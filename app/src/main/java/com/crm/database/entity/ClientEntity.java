package com.crm.database.entity;

import com.crm.Utils.Constants;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Constants.TABLE_CLIENT)
public class ClientEntity {
    @PrimaryKey(autoGenerate = true)
    private long clientId; // auto
    @NonNull
    private String clientGroup; // AUTO 1
    @NonNull
    private String clientCompanyName;
    @NonNull
    private String clientContactPerson1;
    @NonNull
    private String clientDesignation1;
    @NonNull
    private String clientEmailId1; //5
    @NonNull
    private String clientMobileCountryCode1;
    @NonNull
    private String clientMobileNumber1;

    @NonNull
    private String clientStdCode1;
    @NonNull
    private String clientLandLineNumber1;
    @NonNull
    private String clientExtensionNumber1; //10

    @NonNull
    private String clientAddressFirstLine1;
    @NonNull
    private String clientAddressSecondLine1;
    @NonNull
    private String country1;
    @NonNull
    private String state1;
    @NonNull
    private String city1; //15
    @NonNull
    private String pinCode1;


    @NonNull
    private String clientSource; // reference -- cold

    @NonNull
    private String referenceEmpId; // reference -- cold

    @NonNull
    private String doc; //date of contact
    @NonNull

    private String serviceRequired; // a,b,c,d,e


    @NonNull
    private String firstStatus; // interested or not interested or onHold  //20
    @NonNull
    private String firstComments; // not manditory

    // if interested
    @NonNull
    private String meetingDate;
    @NonNull
    private String meetingTime;

    @NonNull
    private String nextContactPerson;

    @NonNull
    private String clientContactPerson2;
    @NonNull
    private String clientDesignation2;
    @NonNull
    private String clientEmailId2;

    @NonNull
    private String clientMobileCountryCode2;
    @NonNull
    private String clientMobileNumber2;

    @NonNull
    private String clientStdCode2;
    @NonNull
    private String clientLandLineNumber2;
    @NonNull
    private String clientExtensionNumber2;

    @NonNull
    private String clientAddressFirstLine2;
    @NonNull
    private String clientAddressSecondLine2;
    @NonNull
    private String country2;
    @NonNull
    private String state2;
    @NonNull
    private String city2;
    @NonNull
    private String pinCode2;


    @NonNull
    private String secondStatus;  // not using this rt now
    @NonNull
    private String secondComments; // outcome of meeting
    @NonNull
    private String followUpMeeting;
    @NonNull
    private String surveyDone;
    @NonNull
    private String surveyDate;
    @NonNull
    private String salesPersonId;

    public ClientEntity(@NonNull String clientGroup, @NonNull String clientCompanyName, @NonNull String clientContactPerson1, @NonNull String clientDesignation1, @NonNull String clientEmailId1, @NonNull String clientMobileCountryCode1, @NonNull String clientMobileNumber1, @NonNull String clientStdCode1, @NonNull String clientLandLineNumber1, @NonNull String clientExtensionNumber1, @NonNull String clientAddressFirstLine1, @NonNull String clientAddressSecondLine1, @NonNull String country1, @NonNull String state1, @NonNull String city1, @NonNull String pinCode1, @NonNull String clientSource, @NonNull String referenceEmpId, @NonNull String doc, @NonNull String serviceRequired, @NonNull String firstStatus, @NonNull String firstComments, @NonNull String meetingDate, @NonNull String meetingTime, @NonNull String nextContactPerson, @NonNull String clientContactPerson2, @NonNull String clientDesignation2, @NonNull String clientEmailId2, @NonNull String clientMobileCountryCode2, @NonNull String clientMobileNumber2, @NonNull String clientStdCode2, @NonNull String clientLandLineNumber2, @NonNull String clientExtensionNumber2, @NonNull String clientAddressFirstLine2, @NonNull String clientAddressSecondLine2, @NonNull String country2, @NonNull String state2, @NonNull String city2, @NonNull String pinCode2, @NonNull String secondStatus, @NonNull String secondComments, @NonNull String followUpMeeting, @NonNull String surveyDone, @NonNull String surveyDate, @NonNull String salesPersonId) {
        this.clientGroup = clientGroup;
        this.clientCompanyName = clientCompanyName;
        this.clientContactPerson1 = clientContactPerson1;
        this.clientDesignation1 = clientDesignation1;
        this.clientEmailId1 = clientEmailId1;
        this.clientMobileCountryCode1 = clientMobileCountryCode1;
        this.clientMobileNumber1 = clientMobileNumber1;
        this.clientStdCode1 = clientStdCode1;
        this.clientLandLineNumber1 = clientLandLineNumber1;
        this.clientExtensionNumber1 = clientExtensionNumber1;
        this.clientAddressFirstLine1 = clientAddressFirstLine1;
        this.clientAddressSecondLine1 = clientAddressSecondLine1;
        this.country1 = country1;
        this.state1 = state1;
        this.city1 = city1;
        this.pinCode1 = pinCode1;
        this.clientSource = clientSource;
        this.referenceEmpId = referenceEmpId;
        this.doc = doc;
        this.serviceRequired = serviceRequired;
        this.firstStatus = firstStatus;
        this.firstComments = firstComments;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
        this.nextContactPerson = nextContactPerson;
        this.clientContactPerson2 = clientContactPerson2;
        this.clientDesignation2 = clientDesignation2;
        this.clientEmailId2 = clientEmailId2;
        this.clientMobileCountryCode2 = clientMobileCountryCode2;
        this.clientMobileNumber2 = clientMobileNumber2;
        this.clientStdCode2 = clientStdCode2;
        this.clientLandLineNumber2 = clientLandLineNumber2;
        this.clientExtensionNumber2 = clientExtensionNumber2;
        this.clientAddressFirstLine2 = clientAddressFirstLine2;
        this.clientAddressSecondLine2 = clientAddressSecondLine2;
        this.country2 = country2;
        this.state2 = state2;
        this.city2 = city2;
        this.pinCode2 = pinCode2;
        this.secondStatus = secondStatus;
        this.secondComments = secondComments;
        this.followUpMeeting = followUpMeeting;
        this.surveyDone = surveyDone;
        this.surveyDate = surveyDate;
        this.salesPersonId = salesPersonId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    @NonNull
    public String getClientGroup() {
        return clientGroup;
    }

    public void setClientGroup(@NonNull String clientGroup) {
        this.clientGroup = clientGroup;
    }

    @NonNull
    public String getClientCompanyName() {
        return clientCompanyName;
    }

    public void setClientCompanyName(@NonNull String clientCompanyName) {
        this.clientCompanyName = clientCompanyName;
    }

    @NonNull
    public String getClientContactPerson1() {
        return clientContactPerson1;
    }

    public void setClientContactPerson1(@NonNull String clientContactPerson1) {
        this.clientContactPerson1 = clientContactPerson1;
    }

    @NonNull
    public String getClientDesignation1() {
        return clientDesignation1;
    }

    public void setClientDesignation1(@NonNull String clientDesignation1) {
        this.clientDesignation1 = clientDesignation1;
    }

    @NonNull
    public String getClientEmailId1() {
        return clientEmailId1;
    }

    public void setClientEmailId1(@NonNull String clientEmailId1) {
        this.clientEmailId1 = clientEmailId1;
    }

    @NonNull
    public String getClientMobileCountryCode1() {
        return clientMobileCountryCode1;
    }

    public void setClientMobileCountryCode1(@NonNull String clientMobileCountryCode1) {
        this.clientMobileCountryCode1 = clientMobileCountryCode1;
    }

    @NonNull
    public String getClientMobileNumber1() {
        return clientMobileNumber1;
    }

    public void setClientMobileNumber1(@NonNull String clientMobileNumber1) {
        this.clientMobileNumber1 = clientMobileNumber1;
    }

    @NonNull
    public String getClientStdCode1() {
        return clientStdCode1;
    }

    public void setClientStdCode1(@NonNull String clientStdCode1) {
        this.clientStdCode1 = clientStdCode1;
    }

    @NonNull
    public String getClientLandLineNumber1() {
        return clientLandLineNumber1;
    }

    public void setClientLandLineNumber1(@NonNull String clientLandLineNumber1) {
        this.clientLandLineNumber1 = clientLandLineNumber1;
    }

    @NonNull
    public String getClientExtensionNumber1() {
        return clientExtensionNumber1;
    }

    public void setClientExtensionNumber1(@NonNull String clientExtensionNumber1) {
        this.clientExtensionNumber1 = clientExtensionNumber1;
    }

    @NonNull
    public String getClientAddressFirstLine1() {
        return clientAddressFirstLine1;
    }

    public void setClientAddressFirstLine1(@NonNull String clientAddressFirstLine1) {
        this.clientAddressFirstLine1 = clientAddressFirstLine1;
    }

    @NonNull
    public String getClientAddressSecondLine1() {
        return clientAddressSecondLine1;
    }

    public void setClientAddressSecondLine1(@NonNull String clientAddressSecondLine1) {
        this.clientAddressSecondLine1 = clientAddressSecondLine1;
    }

    @NonNull
    public String getCountry1() {
        return country1;
    }

    public void setCountry1(@NonNull String country1) {
        this.country1 = country1;
    }

    @NonNull
    public String getState1() {
        return state1;
    }

    public void setState1(@NonNull String state1) {
        this.state1 = state1;
    }

    @NonNull
    public String getCity1() {
        return city1;
    }

    public void setCity1(@NonNull String city1) {
        this.city1 = city1;
    }

    @NonNull
    public String getPinCode1() {
        return pinCode1;
    }

    public void setPinCode1(@NonNull String pinCode1) {
        this.pinCode1 = pinCode1;
    }

    @NonNull
    public String getClientSource() {
        return clientSource;
    }

    public void setClientSource(@NonNull String clientSource) {
        this.clientSource = clientSource;
    }

    @NonNull
    public String getDoc() {
        return doc;
    }

    public void setDoc(@NonNull String doc) {
        this.doc = doc;
    }

    @NonNull
    public String getServiceRequired() {
        return serviceRequired;
    }

    public void setServiceRequired(@NonNull String serviceRequired) {
        this.serviceRequired = serviceRequired;
    }

    @NonNull
    public String getFirstStatus() {
        return firstStatus;
    }

    public void setFirstStatus(@NonNull String firstStatus) {
        this.firstStatus = firstStatus;
    }

    @NonNull
    public String getFirstComments() {
        return firstComments;
    }

    public void setFirstComments(@NonNull String firstComments) {
        this.firstComments = firstComments;
    }

    @NonNull
    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(@NonNull String meetingDate) {
        this.meetingDate = meetingDate;
    }

    @NonNull
    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(@NonNull String meetingTime) {
        this.meetingTime = meetingTime;
    }

    @NonNull
    public String getNextContactPerson() {
        return nextContactPerson;
    }

    public void setNextContactPerson(@NonNull String nextContactPerson) {
        this.nextContactPerson = nextContactPerson;
    }

    @NonNull
    public String getClientContactPerson2() {
        return clientContactPerson2;
    }

    public void setClientContactPerson2(@NonNull String clientContactPerson2) {
        this.clientContactPerson2 = clientContactPerson2;
    }

    @NonNull
    public String getClientDesignation2() {
        return clientDesignation2;
    }

    public void setClientDesignation2(@NonNull String clientDesignation2) {
        this.clientDesignation2 = clientDesignation2;
    }

    @NonNull
    public String getClientEmailId2() {
        return clientEmailId2;
    }

    public void setClientEmailId2(@NonNull String clientEmailId2) {
        this.clientEmailId2 = clientEmailId2;
    }

    @NonNull
    public String getClientMobileCountryCode2() {
        return clientMobileCountryCode2;
    }

    public void setClientMobileCountryCode2(@NonNull String clientMobileCountryCode2) {
        this.clientMobileCountryCode2 = clientMobileCountryCode2;
    }

    @NonNull
    public String getClientMobileNumber2() {
        return clientMobileNumber2;
    }

    public void setClientMobileNumber2(@NonNull String clientMobileNumber2) {
        this.clientMobileNumber2 = clientMobileNumber2;
    }

    @NonNull
    public String getClientStdCode2() {
        return clientStdCode2;
    }

    public void setClientStdCode2(@NonNull String clientStdCode2) {
        this.clientStdCode2 = clientStdCode2;
    }

    @NonNull
    public String getClientLandLineNumber2() {
        return clientLandLineNumber2;
    }

    public void setClientLandLineNumber2(@NonNull String clientLandLineNumber2) {
        this.clientLandLineNumber2 = clientLandLineNumber2;
    }

    @NonNull
    public String getClientExtensionNumber2() {
        return clientExtensionNumber2;
    }

    public void setClientExtensionNumber2(@NonNull String clientExtensionNumber2) {
        this.clientExtensionNumber2 = clientExtensionNumber2;
    }

    @NonNull
    public String getClientAddressFirstLine2() {
        return clientAddressFirstLine2;
    }

    public void setClientAddressFirstLine2(@NonNull String clientAddressFirstLine2) {
        this.clientAddressFirstLine2 = clientAddressFirstLine2;
    }

    @NonNull
    public String getClientAddressSecondLine2() {
        return clientAddressSecondLine2;
    }

    public void setClientAddressSecondLine2(@NonNull String clientAddressSecondLine2) {
        this.clientAddressSecondLine2 = clientAddressSecondLine2;
    }

    @NonNull
    public String getCountry2() {
        return country2;
    }

    public void setCountry2(@NonNull String country2) {
        this.country2 = country2;
    }

    @NonNull
    public String getState2() {
        return state2;
    }

    public void setState2(@NonNull String state2) {
        this.state2 = state2;
    }

    @NonNull
    public String getCity2() {
        return city2;
    }

    public void setCity2(@NonNull String city2) {
        this.city2 = city2;
    }

    @NonNull
    public String getPinCode2() {
        return pinCode2;
    }

    public void setPinCode2(@NonNull String pinCode2) {
        this.pinCode2 = pinCode2;
    }

    @NonNull
    public String getSecondStatus() {
        return secondStatus;
    }

    public void setSecondStatus(@NonNull String secondStatus) {
        this.secondStatus = secondStatus;
    }

    @NonNull
    public String getSecondComments() {
        return secondComments;
    }

    public void setSecondComments(@NonNull String secondComments) {
        this.secondComments = secondComments;
    }

    @NonNull
    public String getFollowUpMeeting() {
        return followUpMeeting;
    }

    public void setFollowUpMeeting(@NonNull String followUpMeeting) {
        this.followUpMeeting = followUpMeeting;
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
    public String getSalesPersonId() {
        return salesPersonId;
    }

    public void setSalesPersonId(@NonNull String salesPersonId) {
        this.salesPersonId = salesPersonId;
    }

    @NonNull
    public String getReferenceEmpId() {
        return referenceEmpId;
    }

    public void setReferenceEmpId(@NonNull String referenceEmpId) {
        this.referenceEmpId = referenceEmpId;
    }
}