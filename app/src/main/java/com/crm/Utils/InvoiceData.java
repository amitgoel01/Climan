package com.crm.Utils;

public class InvoiceData {
        private String companyName;
        private String addressFirstLine;
        private String addressSecondLine;
        private String contactPerson;
        private String revenue;
        private String path;

    public InvoiceData(String companyName, String addressFirstLine, String addressSecondLine,
                       String contactPerson, String revenue, String path) {
        this.companyName = companyName;
        this.addressFirstLine = addressFirstLine;
        this.addressSecondLine = addressSecondLine;
        this.contactPerson = contactPerson;
        this.path = path;
    }

    public String getRevenue() {
        return revenue;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getAddressFirstLine() {
        return addressFirstLine;
    }

    public String getAddressSecondLine() {
        return addressSecondLine;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "InvoiceData{" +
                "companyName='" + companyName + '\'' +
                ", addressFirstLine='" + addressFirstLine + '\'' +
                ", addressSecondLine='" + addressSecondLine + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", revenue='" + revenue + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
