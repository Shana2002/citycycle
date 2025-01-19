package com.example.citycycle.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.sql.Blob;

public class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String paymentInfo;
    private byte[] image;

    public User() {
    }

    public User(int userId, String name, String email, String password, String phone, String paymentInfo,byte[] image) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.paymentInfo = paymentInfo;
        this.image = image;
    }

    public int getUserId() {
        return userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Bitmap getImage(){
        if (image != null){
            return  BitmapFactory.decodeByteArray(image, 0, image.length);
        }
        else {
            return null;
        }
    }

    public byte[] getImageBlob(){
        return image;
    }
}
