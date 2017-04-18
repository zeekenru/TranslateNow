package com.kovapss.transaltenow.DI;


import com.kovapss.transaltenow.Model.Db.DbHelper;
import com.kovapss.transaltenow.Model.Db.DbHelperImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DbModule {

    @Singleton
    @Provides
    DbHelper provideDbHelper(){
        return new DbHelperImpl();
    }



}
