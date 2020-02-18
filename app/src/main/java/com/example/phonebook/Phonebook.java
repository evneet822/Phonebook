package com.example.phonebook;

public class Phonebook {


    int id;
    String firstName , lastName, address;
    int phoneNumber;

    public Phonebook(int id, String firstName, String lastName, String address, int phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }
}
