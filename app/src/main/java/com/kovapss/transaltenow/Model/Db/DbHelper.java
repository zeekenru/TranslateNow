package com.kovapss.transaltenow.Model.Db;


import com.kovapss.transaltenow.Model.Entities.TranslateModel;

import io.reactivex.Observable;
import io.realm.RealmResults;

public interface DbHelper {
    RealmResults<TranslateModel> getHistory();
    RealmResults<TranslateModel> getFavorites();
    void addToHistory(TranslateModel model);
    void addToFavorites(TranslateModel model);
    void deleteHistory();
    void deleteFavorites();
    void deleteFromFavorites(TranslateModel model);
}
