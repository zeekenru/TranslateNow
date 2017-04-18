package com.kovapss.transaltenow.Model.Entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModel {

    @SerializedName("code")
    @Expose
    public Integer code;
    @SerializedName("lang")
    @Expose
    public String languages;
    @SerializedName("text")
    @Expose
    public List<String> translatedText = null;

    public String originalText;

    public ResponseModel() {
    }

    @Override
    public String toString() {
        return "Translate : " + "\n" + "lang : " + languages + "\n" + "original text : "
                + originalText +"\n" + "translatedText : " + translatedText.toString();
    }
}
