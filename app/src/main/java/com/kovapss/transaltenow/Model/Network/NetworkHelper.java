package com.kovapss.transaltenow.Model.Network;


import com.kovapss.transaltenow.Model.Entities.AvailableLanguages;
import com.kovapss.transaltenow.Model.Entities.ResponseModel;

import io.reactivex.Observable;

public interface NetworkHelper {

    Observable<ResponseModel> getTranslate(String text, String lang);
    Observable<AvailableLanguages> getLanguages(String language);
    boolean hasConnection();

}
