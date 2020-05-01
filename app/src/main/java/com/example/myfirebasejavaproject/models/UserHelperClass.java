package com.example.myfirebasejavaproject.models;

public class UserHelperClass {
    String name,username,email,phoneNo,password,address,type,mImageUrl;
   public static boolean whichUser = false;
   public static String shared = "HomeCookerDetails";



    public UserHelperClass() {

    }


    public UserHelperClass(String name, String username, String email, String phoneNo, String password, String address,String type) {
        this.name = name;
        this.address =address;
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
        this.type = type;
    }

    public UserHelperClass(String name, String username, String email, String phoneNo, String password, String address,String type,String image) {
        this.name = name;
        this.address =address;
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
        this.password = password;
        this.type = type;
        this.mImageUrl = image;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
