package com.example.signuploginfirebase;

import android.provider.ContactsContract;

public class DataClass {
    private String post_id;
    private String datatitle;
    private String datadesc;
    private String temp;
    private String dataImg;
    private String key;
    private String currentLocation;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDatatitle() {
        return datatitle;
    }

    public String getDatadesc() {
        return datadesc;
    }

    public String getTemp() {
        return temp;
    }

    public String getDataImg() {
        return dataImg;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public DataClass(String post_id,String datatitle, String datadesc, String temp, String dataImg, String currentLocation) {
        this.datatitle = datatitle;
        this.post_id=post_id;
        this.datadesc = datadesc;
        this.temp = temp;
        this.dataImg = dataImg;
        this.currentLocation = currentLocation;
    }
    public DataClass(String datatitle, String datadesc, String temp, String dataImg, String currentLocation) {
        this.datatitle = datatitle;
        this.datadesc = datadesc;
        this.temp = temp;
        this.dataImg = dataImg;
        this.currentLocation = currentLocation;
    }
    public DataClass(){}


}
