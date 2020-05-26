package com.crm.database.entity;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

public class SalesEntity {
    @PrimaryKey(autoGenerate = true)
    private long salesNumber;
    @NonNull
    private String clientGroup; // AUTO
    @NonNull
    private String clientCompanyName;
    @NonNull
    private String clientId; // AUTO
    @NonNull
    private String clientContactPerson1;
    @NonNull
    private String clientDesignation1;
    @NonNull
    private String clientEmailId1;
    @NonNull
    private String clientStdCode1;
    @NonNull
    private String clientLandLineNumber1;
    @NonNull
    private String clientExtensionNumber1;
    @NonNull
    private String clientMobileCountryCode1;
    @NonNull
    private String clientMobileNumber1;
    @NonNull
    private String clientAddressFirstLine1;
    @NonNull
    private String clientAddressSecondLine1;
    @NonNull
    private String country1;
    @NonNull
    private String state1;
    @NonNull
    private String city1;
    @NonNull
    private String pinCode1;


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
    private String clientContactPerson2;
    @NonNull
    private String clientDesignation2;
    @NonNull
    private String clientEmailId2;
    @NonNull
    private String clientStdCode2;
    @NonNull
    private String clientLandLineNumber2;
    @NonNull
    private String clientExtensionNumber2;
    @NonNull
    private String clientMobileCountryCode2;
    @NonNull
    private String clientMobileNumber2;
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
