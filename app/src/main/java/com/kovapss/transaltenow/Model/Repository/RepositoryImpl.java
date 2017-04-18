package com.kovapss.transaltenow.Model.Repository;


import com.kovapss.transaltenow.Application;
import com.kovapss.transaltenow.Model.Db.DbHelper;
import com.kovapss.transaltenow.Model.Entities.AvailableLanguages;
import com.kovapss.transaltenow.Model.Entities.TranslateModel;
import com.kovapss.transaltenow.Model.Network.NetworkHelper;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmResults;

public class RepositoryImpl implements Repository {

    @Inject
    NetworkHelper mNetworkHelper;

    @Inject
    DbHelper mDbHelper;

    public RepositoryImpl() {
        Application.getAppComponent().injectRepository(this);
        Logger.d("Repository was created");
    }

    @Override
    public Observable<TranslateModel> translate(String text, String language){
        return mNetworkHelper.getTranslate(text,  language)
                .map(responseModel -> {
                    TranslateModel model = new TranslateModel();
                    model.setOriginalText(text);
                    model.setTranslatedText(responseModel.translatedText.get(0));
                    model.setLanguages(responseModel.languages);
                    return model;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(this::saveTranslate);
    }

    @Override
    public Observable<AvailableLanguages> getLanguages(String language) {
        return mNetworkHelper.getLanguages(language);
    }

    @Override
    public RealmResults<TranslateModel> getHistory(){
         return mDbHelper.getHistory();
    }

    @Override
    public void addToFavorites(String inputText, String resultText, String languages) {
        mDbHelper.addToFavorites(new TranslateModel(inputText, resultText, languages));
    }

    @Override
    public RealmResults<TranslateModel> getFavorites() {
        return mDbHelper.getFavorites();
    }

    @Override
    public void clearFavorites() {
        mDbHelper.deleteFavorites();
    }

    @Override
    public void clearHistory() {
        mDbHelper.deleteHistory();
    }

    @Override
    public void deleteFromFavorite(String inputText, String resultText) {
          mDbHelper.deleteFromFavorites(new TranslateModel(inputText, resultText));
    }

    private void saveTranslate(TranslateModel model){
        mDbHelper.addToHistory(model);
    }

}
