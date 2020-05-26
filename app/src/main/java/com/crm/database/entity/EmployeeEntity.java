package com.crm.database.entity;


import com.crm.Utils.Constants;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = Constants.TABLE_EMPLOYEE)
public class EmployeeEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long empNumber;
    @NonNull
    private String empName;
    @NonNull
    private String empId;
    @NonNull
    private String businessUnit;
    @NonNull
    private String emailId;
    @NonNull
    private String country;
    @NonNull
    private String city;
    @NonNull
    private String state;
    @NonNull
    private String designation;
    @NonNull
    private String jobType;
    @NonNull
    private String rpManager;
    @NonNull
    private String phNumber;
    @NonNull
    private String doj;
    @NonNull
    private String dob;
    @NonNull
    private String tenure;

    public EmployeeEntity(String empName, String empId, String businessUnit, String emailId, String country, String city, String state, String designation, String jobType, String rpManager, String phNumber, String doj, String dob, String tenure) {
      this.empName = empName;
        this.empId = empId;
        this.businessUnit = businessUnit;
        this.emailId = emailId;
        this.country = country;
        this.city = city;
        this.state = state;
        this.designation = designation;
        this.jobType = jobType;
        this.rpManager = rpManager;
        this.phNumber = phNumber;
        this.doj = doj;
        this.dob = dob;
        this.tenure = tenure;
    }

    @NonNull
    public String getJobType() {
        return jobType;
    }

    public void setJobType(@NonNull String jobType) {
        this.jobType = jobType;
    }

    public long getEmpNumber() {
        return empNumber;
    }

    public void setEmpNumber(long empNumber) {
        this.empNumber = empNumber;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getRpManager() {
        return rpManager;
    }

    public void setRpManager(String rpManager) {
        this.rpManager = rpManager;
    }

    public String getPhNumber() {
        return phNumber;
    }

    public void setPhNumber(String phNumber) {
        this.phNumber = phNumber;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getTenure() {
        return tenure;
    }

    public void setTenure(String tenure) {
        this.tenure = tenure;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeEntity)) return false;

        EmployeeEntity employee = (EmployeeEntity) o;

        if (empNumber != employee.empNumber) return false;
        return empName != null ? empName.equals(employee.empName) : employee.empName == null;
    }


    @Override
    public int hashCode() {
        int result = (int) empNumber;
        result = 31 * result + (empName != null ? empName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empNumber=" + empNumber +
                ", empName='" + empName + '\'' +
                ", empId='" + empId + '\'' +
                ", businessUnit='" + businessUnit + '\'' +
                ", emailId='" + emailId + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", designation='" + designation + '\'' +
                ", jobType='" + jobType + '\'' +
                ", rpManager='" + rpManager + '\'' +
                ", phNumber='" + phNumber + '\'' +
                ", doj='" + doj + '\'' +
                ", dob='" + dob + '\'' +
                ", tenure='" + tenure + '\'' +
                '}';
    }
}
