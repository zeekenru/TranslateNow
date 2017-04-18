package com.kovapss.transaltenow.Presenter;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kovapss.transaltenow.Application;
import com.kovapss.transaltenow.Model.Repository.Repository;
import com.kovapss.transaltenow.View.SavedList.SavedListView;

import javax.inject.Inject;

@InjectViewState
public class SavedListPresenter extends MvpPresenter<SavedListView> {

    @Inject
    Repository mRepository;

    private String showedDataType;

    public SavedListPresenter() {
        Application.getAppComponent().injectSavedListPresenter(this);
    }


    public void notifyClickDelete() {
        if (showedDataType.equals("Favorite")){
            mRepository.clearFavorites();
        } else {
            mRepository.clearHistory();
        }

    }

    public void init(String showedDataType) {
        this.showedDataType = showedDataType;
        if (showedDataType.equals("Favorite")){
            getViewState().showList(mRepository.getFavorites());
        } else {
            getViewState().showList(mRepository.getHistory());
        }
    }
}
