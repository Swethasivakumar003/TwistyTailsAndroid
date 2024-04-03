package com.example.twistytails; // Update this with your package name

public class Appointment {
    private String customerId;
    private String serviceName;
    private String customerName;
    private String phoneNumber;
    private String bookingDate;
    private String selectedTime;
    private String storePreference;

    // Constructor and other methods here...

    // Getter methods
    public String getCustomerId() {
        return customerId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public String getStorePreference() {
        return storePreference;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }

    public void setStorePreference(String storePreference) {
        this.storePreference = storePreference;
    }
}

