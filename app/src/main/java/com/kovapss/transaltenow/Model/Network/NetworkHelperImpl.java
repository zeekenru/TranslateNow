package com.kovapss.transaltenow.Model.Network;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.kovapss.transaltenow.Application;
import com.kovapss.transaltenow.Model.Entities.AvailableLanguages;
import com.kovapss.transaltenow.Model.Entities.ResponseModel;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import io.reactivex.Observable;

import static com.kovapss.transaltenow.Constans.APP_KEY;

public class NetworkHelperImpl implements NetworkHelper {

    @Inject
    Context context;

    @Inject
    ApiService api;

    public NetworkHelperImpl() {
        Application.getAppComponent().injectNetworkHelper(this);
    }

    @Override
    public Observable<ResponseModel> getTranslate(String text, String lang) {
        return api.translate(APP_KEY, text, lang);
    }

    @Override
    public Observable<AvailableLanguages> getLanguages(String language) {
        return api.getLanguages(APP_KEY, language);
    }

    @Override
    public boolean hasConnection() {
        Logger.d( "was called hasConnection -method");
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
