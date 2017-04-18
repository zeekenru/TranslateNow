package com.kovapss.transaltenow.View.SavedList;


import com.arellomobile.mvp.MvpView;
import com.kovapss.transaltenow.Model.Entities.TranslateModel;

import io.realm.RealmResults;


public interface SavedListView extends MvpView {
    void showList(RealmResults<TranslateModel> data);
}
