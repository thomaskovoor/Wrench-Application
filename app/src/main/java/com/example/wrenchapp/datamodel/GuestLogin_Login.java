package com.example.wrenchapp.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GuestLogin_Login {
    @SerializedName("TOKEN")
    @Expose
    private String TOKEN;

    public String getToken(){return TOKEN;}



}
