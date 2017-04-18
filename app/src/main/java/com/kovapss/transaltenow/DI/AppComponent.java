package com.kovapss.transaltenow.DI;


import com.kovapss.transaltenow.Model.Db.DbHelperImpl;
import com.kovapss.transaltenow.Model.Network.NetworkHelperImpl;
import com.kovapss.transaltenow.Model.Repository.RepositoryImpl;
import com.kovapss.transaltenow.Presenter.MainPresenter;
import com.kovapss.transaltenow.Presenter.SavedListPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules =
        {AppModule.class, DbModule.class})
public interface AppComponent {
       void injectMainPresenter(MainPresenter presenter);
       void injectNetworkHelper(NetworkHelperImpl networkHelper);
       void injectRepository(RepositoryImpl repository);
       void injectSavedListPresenter(SavedListPresenter presenter);
       void injectDbHelper(DbHelperImpl dbHelper);
}
