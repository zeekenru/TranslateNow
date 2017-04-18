package com.kovapss.transaltenow.DI;


import android.content.Context;

import com.kovapss.transaltenow.Application;
import com.kovapss.transaltenow.Model.Network.ApiService;
import com.kovapss.transaltenow.Model.Network.NetworkHelper;
import com.kovapss.transaltenow.Model.Network.NetworkHelperImpl;
import com.kovapss.transaltenow.Model.Repository.Repository;
import com.kovapss.transaltenow.Model.Repository.RepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.kovapss.transaltenow.Constans.BASE_URL;

@Module
public class AppModule {

    private Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Singleton
    @Provides
    Repository provideRepository(){
        return new RepositoryImpl();
    }

    @Singleton
    @Provides
    Context provideContext(){
        return mApplication;
    }

    @Singleton
    @Provides
    OkHttpClient provideHttpClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }

    @Singleton
    @Provides
    ApiService provideApi(OkHttpClient client){
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        return mRetrofit.create(ApiService.class);
    }


    @Singleton
    @Provides
    NetworkHelper proveNetworkHelper(){
        return new NetworkHelperImpl();
    }



}
