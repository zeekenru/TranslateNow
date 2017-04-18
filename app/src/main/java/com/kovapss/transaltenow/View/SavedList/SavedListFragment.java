package com.kovapss.transaltenow.View.SavedList;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kovapss.transaltenow.Model.Entities.TranslateModel;
import com.kovapss.transaltenow.Presenter.SavedListPresenter;
import com.kovapss.transaltenow.R;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;


public class SavedListFragment extends MvpAppCompatFragment implements SavedListView {

    @InjectPresenter
    SavedListPresenter presenter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.history_toolbar)
    Toolbar toolbar;

    public SavedListFragment() {
    }

    public static SavedListFragment newInstance(String showedDataType) {
        Bundle args = new Bundle();
        args.putString("Type", showedDataType);
        SavedListFragment fragment = new SavedListFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_list, container, false);
        ButterKnife.bind(this, view);
        String showedDataType = this.getArguments().getString("Type");
        presenter.init(showedDataType);
        initToolbar();
        initRecyclerView();
        return view;
    }

    private void initToolbar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar =  ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);

    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.saved_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        presenter.notifyClickDelete();
        return true;
    }

    @Override
    public void showList(RealmResults<TranslateModel> data) {
        recyclerView.setAdapter(new RealmAdapter(data));

    }


}
