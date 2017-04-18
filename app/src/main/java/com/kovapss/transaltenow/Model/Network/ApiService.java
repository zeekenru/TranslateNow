package com.kovapss.transaltenow.Model.Network;


import com.kovapss.transaltenow.Model.Entities.AvailableLanguages;
import com.kovapss.transaltenow.Model.Entities.ResponseModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("tr.json/translate?")
    Observable<ResponseModel> translate(@Query("key") String appKey,
                                        @Query("text") String text,
                                        @Query("lang") String lang);
    @GET("tr.json/getLangs?")
    Observable<AvailableLanguages> getLanguages(@Query("key")String appKey,
                                                @Query("ui")String lang);


}
