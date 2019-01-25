package com.example.sqlitewithsearch.Model;

import android.graphics.Bitmap;

public class Friend {

    public int id;
    public String name,address,phone,email;
    public byte[] personImage;


    public Friend(int id, String name, String address,String email, String phone,byte[] personImage) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.personImage = personImage;
    }

    public Friend(){

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getPersonImage() {
        return personImage;
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPersonImage(byte[] personImage) {
        this.personImage = personImage;
    }
}
