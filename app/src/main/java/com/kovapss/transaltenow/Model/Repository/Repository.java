package com.kovapss.transaltenow.Model.Repository;


import com.kovapss.transaltenow.Model.Entities.AvailableLanguages;
import com.kovapss.transaltenow.Model.Entities.TranslateModel;

import io.reactivex.Observable;
import io.realm.RealmResults;

public interface Repository {
    Observable<TranslateModel> translate(String text, String language);
    Observable<AvailableLanguages> getLanguages(String language);
    RealmResults<TranslateModel> getHistory();
    RealmResults<TranslateModel> getFavorites();
    void addToFavorites(String inputText, String resultText, String languages);
    void clearFavorites();
    void clearHistory();
    void deleteFromFavorite(String inputText, String resultText);
}
