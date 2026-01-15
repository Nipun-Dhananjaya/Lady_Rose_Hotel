package com.lady_rose.dto;

public class Person {
    static final int stringLength = 5;
    private String pId;
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private String address;
    private String gender;
    private String nic;

    public Person(){

    }
    public Person(String pId,String name,String phoneNumber,String emailAddress,String address,String gender,String nic){
        this.pId=pId;
        this.name=name;
        this.address=address;
        this.phoneNumber=phoneNumber;
        this.emailAddress=emailAddress;
        this.gender=gender;
        this.nic=nic;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPId() {
        return pId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public String getNic() {
        return nic;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public void setP_Id(String pId) {
        this.pId = pId;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
