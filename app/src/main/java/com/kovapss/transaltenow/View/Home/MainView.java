package com.kovapss.transaltenow.View.Home;


import com.arellomobile.mvp.MvpView;
import com.kovapss.transaltenow.Model.Entities.AvailableLanguages;

import io.reactivex.Observable;

public interface MainView extends MvpView {
    void handleTextChanges();
    void showTranslate(Observable<String> observable);
    void showMessage(String message);
    void setOfflineMode(boolean enabled);
    void showAvailableLanguages(Observable<AvailableLanguages> observable);
    void clearInputText();
    void clearResultText();
    void changeImage();
}
