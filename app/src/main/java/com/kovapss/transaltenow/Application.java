package com.kovapss.transaltenow;


import com.kovapss.transaltenow.DI.AppComponent;
import com.kovapss.transaltenow.DI.AppModule;
import com.kovapss.transaltenow.DI.DaggerAppComponent;
import com.kovapss.transaltenow.DI.DbModule;
import com.orhanobut.logger.Logger;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class Application extends android.app.Application {

    private static AppComponent mAppComponent;

    private final String TAG = "MyLogs";

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
        initRealm();
        Logger.init(TAG);
        Logger.d("application was created");
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }


    private void initAppComponent() {
        if (mAppComponent == null) {
            mAppComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .dbModule(new DbModule())
                    .build();
        }

    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }
}
