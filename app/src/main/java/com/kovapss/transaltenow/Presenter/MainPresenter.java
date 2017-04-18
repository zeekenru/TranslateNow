package com.kovapss.transaltenow.Presenter;


import android.text.TextUtils;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kovapss.transaltenow.Application;
import com.kovapss.transaltenow.Model.Entities.TranslateModel;
import com.kovapss.transaltenow.Model.Network.NetworkHelper;
import com.kovapss.transaltenow.Model.Repository.Repository;
import com.kovapss.transaltenow.View.Home.MainView;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import io.reactivex.schedulers.Schedulers;


@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Inject
    Repository mRepository;

    @Inject
    NetworkHelper networkHelper;

    private final String DEFAULT_LANGUAGE = "ru";

    public MainPresenter() {
        Logger.d("MainPresenter was created");
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Application.getAppComponent().injectMainPresenter(this);
        if (networkHelper.hasConnection()){
            getViewState().showAvailableLanguages(
                    mRepository.getLanguages(DEFAULT_LANGUAGE)
//                            .doOnError(throwable -> {
//                                if (throwable instanceof HttpException){
//                                    handleHttpError(throwable);
//                                }
//                            })
                            .subscribeOn(Schedulers.io()));
        } else {
            getViewState().setOfflineMode(true);
        }
    }

//    private void handleHttpError(Throwable throwable) {
//        HttpException e = (HttpException) throwable;
//        retrofit2.Response response = e.response();
//        Converter<ResponseBody, ErrorModel> converter = new GsonConverterFactory()
//                .responseBodyConverter(ErrorModel.class, new Annotation[0]);
//        ErrorModel error = null;
//        try {
//            error = converter.convert(response.errorBody());
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//        switch (error.code){
//            case "401":
//                getViewState().showMessage("Incorrect API-key");
//                break;
//            case "402":
//                getViewState().showMessage("API-key is blocked");
//                getViewState().showMessage("API-key is blocked");
//                break;
//            case "404":
//            case "413":
//                getViewState().showMessage("Text length limit exceeded");
//                break;
//            case "422":
//            case "501":
//                getViewState().showMessage("Text can not be translated");
//                break;
//        }
//    }

    public void textChanged(String text, String lang) {
        if (networkHelper.hasConnection()) {
            getViewState().showTranslate(mRepository.translate(text, lang)
                    .map(TranslateModel::getTranslatedText)
            );
        } else {
            getViewState().clearResultText();
            getViewState().setOfflineMode(true);
        }
    }

    public void clickFavorite(String inputText, String resultText, String languages){
        mRepository.addToFavorites(inputText, resultText, languages);
        getViewState().changeImage();
    }

    public void clickClear() {
        getViewState().clearInputText();
        getViewState().clearResultText();
    }

    public void clickUnFavorite(String inputText, String resultText) {
        mRepository.deleteFromFavorite(inputText, resultText);
        getViewState().changeImage();
    }

    public void languagesChanged(String inputText, String languagesString) {
        if (!TextUtils.isEmpty(inputText)){
            getViewState().clearResultText();
            textChanged(inputText, languagesString);
        }
    }

    public void clickUpdate() {
        if (networkHelper.hasConnection()){
            getViewState().setOfflineMode(false);
            getViewState().showAvailableLanguages(
                    mRepository.getLanguages(DEFAULT_LANGUAGE)
                            .subscribeOn(Schedulers.io()));
        } else {
            getViewState().showMessage("Try again later");
        }
    }
}
