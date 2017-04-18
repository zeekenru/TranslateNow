package com.kovapss.transaltenow.Model.Entities;


import io.realm.RealmObject;
import io.realm.annotations.Required;

public class TranslateModel extends RealmObject {

    public TranslateModel(String originalText, String translatedText, String languages, String type) {
        this.originalText = originalText;
        this.translatedText = translatedText;
        this.languages = languages;
        this.type = type;
    }

    public TranslateModel(String originalText, String translatedText, String languages) {
        this.originalText = originalText;
        this.translatedText = translatedText;
        this.languages = languages;
    }

    public TranslateModel(String originalText, String translatedText) {
        this.originalText = originalText;
        this.translatedText = translatedText;
    }


    public TranslateModel(){

    }

    @Required
    private String originalText;
    @Required
    private String translatedText;
    @Required
    private String languages;

    private String type;

    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
