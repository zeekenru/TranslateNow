package com.kovapss.transaltenow.Model.Entities;


import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class AvailableLanguages {
    @SerializedName("langs")
    public HashMap<String, String> languages;

    @Override
    public String toString() {
        return languages.toString();
    }
}
