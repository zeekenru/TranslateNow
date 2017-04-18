package com.kovapss.transaltenow.Model.Entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorModel {
    @SerializedName("code")
    @Expose
    public String code;

    @SerializedName("message")
    @Expose
    public String message;

}
