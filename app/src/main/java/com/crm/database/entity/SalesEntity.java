package com.crm.database.entity;

import androidx.annotation.NonNull;

public class SalesEntity {

    @NonNull
    private String clientGroup; // AUTO
    @NonNull
    private String clientCompanyName;
    @NonNull
    private String clientId; // AUTO
    @NonNull
    private String contactPerson;
    @NonNull
    private String clientDesignation;
    @NonNull
    private String clientEmailId;
    @NonNull
    private String clientStdCode;
    @NonNull
    private String clientLandLineNumber;
    @NonNull
    private String clientExtensionNumber;
    @NonNull
    private String clientMobileCountryCode;
    @NonNull
    private String clientMobileNumber;
    @NonNull
    private String clientAddressFirstLine;
    @NonNull
    private String clientAddressSecondLine;
    @NonNull
    private String country;
    @NonNull
    private String state;
    @NonNull
    private String city;
    @NonNull
    private String pinCode;
    @NonNull
    private String clientSource; // reference -- cold
    @NonNull
    private String doc; //date of contact
    @NonNull
    private String serviceRequired; // a,b,c,d,e

    @NonNull
    private String firstStatus; // interested or not interested or onHold
    @NonNull
    private String firstComments; // not manditory

    // if interested
    @NonNull
    private String nMeetingDate;
    @NonNull
    private String nMeetingTime;

    @NonNull
    private String contactPerson2;

    @NonNull
    private String secondStatus;
    @NonNull
    private String secondComments; // outcome of meeting
    @NonNull
    private String followUpMeeting;
    @NonNull
    private String surveyDone;
    @NonNull
    private String surveyDate;
}
