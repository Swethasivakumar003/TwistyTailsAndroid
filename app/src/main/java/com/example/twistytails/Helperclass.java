package com.example.twistytails;

public class Helperclass {
    private String name;
    private String phoneNo;
    private String email;
    private String password;


    // Default constructor required for calls to DataSnapshot.getValue(HelperClass.class)
    public Helperclass() {
    }

    public Helperclass(String name, String phoneNo, String email, String password) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.email = email;
        this.password = password;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
