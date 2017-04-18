package com.kovapss.transaltenow.View;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.kovapss.transaltenow.R;
import com.kovapss.transaltenow.View.Home.MainFragment;
import com.kovapss.transaltenow.View.SavedList.SavedListFragment;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {


    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("activity was created");
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initBottomNavigation();
        if(savedInstanceState == null){
           showCurrentFragment(new MainFragment());
        }
    }



    private void initBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.menu_home:
                    showCurrentFragment(new MainFragment());
                    break;
                case R.id.menu_favorites:
                    showCurrentFragment(SavedListFragment.newInstance("Favorite"));
                    break;
                case R.id.menu_history:
                    showCurrentFragment(SavedListFragment.newInstance("History"));
                    break;
            }
            return true;
        });
    }


    private void showCurrentFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction;
        transaction = fragmentManager.beginTransaction();

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.fragment_container, fragment);

        transaction.commit();
    }

}
