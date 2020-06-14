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
    private String clientGroupName; // AUTO 1

    @NonNull
    private String clientCompanyName;

    @NonNull
    private String clientAddressFirstLine1;
    @NonNull
    private String clientAddressSecondLine1;
    @NonNull
    private String clientCountry;
    @NonNull
    private String clientState;
    @NonNull
    private String clientCity; //15
    @NonNull
    private String clientPinCode;

    @NonNull
    private String clientSource; // reference -- cold
    // if interested
    private String timeStamp;

    @NonNull
    private String salesPersonId;

    public ClientEntity(@NonNull String clientGroup, @NonNull String clientGroupName, @NonNull String clientCompanyName, @NonNull String clientAddressFirstLine1, @NonNull String clientAddressSecondLine1, @NonNull String clientCountry, @NonNull String clientState, @NonNull String clientCity, @NonNull String clientPinCode, @NonNull String clientSource, String timeStamp, @NonNull String salesPersonId) {
        this.clientGroup = clientGroup;
        this.clientGroupName = clientGroupName;
        this.clientCompanyName = clientCompanyName;
        this.clientAddressFirstLine1 = clientAddressFirstLine1;
        this.clientAddressSecondLine1 = clientAddressSecondLine1;
        this.clientCountry = clientCountry;
        this.clientState = clientState;
        this.clientCity = clientCity;
        this.clientPinCode = clientPinCode;
        this.clientSource = clientSource;
        this.timeStamp = timeStamp;
        this.salesPersonId = salesPersonId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getClientId() {
        return clientId;
    }

    @NonNull
    public String getClientGroup() {
        return clientGroup;
    }


    @NonNull
    public String getClientGroupName() {
        return clientGroupName;
    }

    public void setClientGroupName(@NonNull String clientGroupName) {
        this.clientGroupName = clientGroupName;
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
    public String getClientCountry() {
        return clientCountry;
    }

    public void setClientCountry(@NonNull String clientCountry) {
        this.clientCountry = clientCountry;
    }

    @NonNull
    public String getClientState() {
        return clientState;
    }

    public void setClientState(@NonNull String clientState) {
        this.clientState = clientState;
    }

    @NonNull
    public String getClientCity() {
        return clientCity;
    }

    public void setClientCity(@NonNull String clientCity) {
        this.clientCity = clientCity;
    }

    @NonNull
    public String getClientPinCode() {
        return clientPinCode;
    }

    public void setClientPinCode(@NonNull String clientPinCode) {
        this.clientPinCode = clientPinCode;
    }

    @NonNull
    public String getClientSource() {
        return clientSource;
    }

    public void setClientSource(@NonNull String clientSource) {
        this.clientSource = clientSource;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @NonNull
    public String getSalesPersonId() {
        return salesPersonId;
    }

    public void setSalesPersonId(@NonNull String salesPersonId) {
        this.salesPersonId = salesPersonId;
    }
}