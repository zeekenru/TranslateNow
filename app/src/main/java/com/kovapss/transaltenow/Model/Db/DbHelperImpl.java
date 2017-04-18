package com.kovapss.transaltenow.Model.Db;


import android.content.Context;

import com.kovapss.transaltenow.Application;
import com.kovapss.transaltenow.Model.Entities.TranslateModel;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;


public class DbHelperImpl implements DbHelper{


    @Inject
    Context mContext;

    private Realm mRealm;
    private final String FAVORITE_TYPE = "favorite";
    private final String HISTORY_TYPE = "history";

    public DbHelperImpl() {
        Application.getAppComponent().injectDbHelper(this);
        mRealm = Realm.getDefaultInstance();
        Logger.d("DbHelper was created");
    }

    @Override
    public void addToFavorites(TranslateModel model) {
        Logger.d("was called addToFavorites -method");
        mRealm.executeTransaction(realm -> {
            model.setType(FAVORITE_TYPE);
            realm.copyToRealm(model);
        });
    }

    @Override
    public void addToHistory(TranslateModel model) {
        Logger.d("was called addToHistory -method");
        mRealm.executeTransaction(realm -> {
            model.setType(HISTORY_TYPE);
            realm.copyToRealm(model);
           });
    }


    @Override
    public RealmResults<TranslateModel> getHistory() {
        Logger.d("was called getHistory -method ");
        RealmResults<TranslateModel> results =
                mRealm.where(TranslateModel.class).equalTo("type", HISTORY_TYPE).findAll();
        return results;
    }


    @Override
    public void deleteHistory() {
        Logger.d("was calling deleteHistory -method");
        mRealm.executeTransaction(realm -> realm.where(TranslateModel.class)
                .equalTo("type", HISTORY_TYPE).findAll().deleteAllFromRealm());
    }

    @Override
    public void deleteFavorites() {
        Logger.d("was calling deleteFavorites -method");
        mRealm.executeTransaction(realm -> realm.where(TranslateModel.class)
                .equalTo("type", FAVORITE_TYPE).findAll().deleteAllFromRealm());
    }

    @Override
    public void deleteFromFavorites(TranslateModel model) {
        Logger.d("was calling deleteFromFavorites -method");
        mRealm.executeTransaction(realm -> realm.where(TranslateModel.class)
                .equalTo("originalText", model.getOriginalText())
                .or()
                .equalTo("translatedText", model.getTranslatedText())
                .findAll()
                .deleteAllFromRealm()
        );
    }


    @Override
    public RealmResults<TranslateModel> getFavorites() {
        Logger.d("was calling getFavorites -method");
        return mRealm.where(TranslateModel.class).equalTo("type", FAVORITE_TYPE).findAll();
    }

}
