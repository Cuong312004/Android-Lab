package com.example.bai2;

public class Contact {
    private int id;
    private String name;
    private String phoneNumber;
    public Contact(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phone;
    }

    public Contact() {

    }
    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
