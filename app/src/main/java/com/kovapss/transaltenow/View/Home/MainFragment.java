package com.kovapss.transaltenow.View.Home;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.kovapss.transaltenow.Model.Entities.AvailableLanguages;
import com.kovapss.transaltenow.Presenter.MainPresenter;
import com.kovapss.transaltenow.R;
import com.orhanobut.logger.Logger;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class MainFragment extends MvpAppCompatFragment implements MainView {

    @InjectPresenter
    MainPresenter presenter;

    @BindView(R.id.resultText)
    TextView resultText;

    @BindView(R.id.input)
    EditText inputText;

    @BindView(R.id.yandex_link)
    TextView yandexText;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.result_layout)
    ConstraintLayout resultLayout;

    @BindView(R.id.offline_preview)
    RelativeLayout offlineLayout;

    @BindView(R.id.root_layout)
    RelativeLayout rootLayout;

    @BindView(R.id.addto_favorite_button_image)
    ImageView favoriteImage;

    @BindView(R.id.clear_action_image)
    ImageView clearImage;

    @BindView(R.id.update_button)
    AppCompatButton updateButton;

    @BindView(R.id.spinner_to)
    MaterialSpinner spinnerTo;

    @BindView(R.id.spinner_from)
    MaterialSpinner spinnerFrom;


    private CompositeDisposable mCompositeDisposable;
    private boolean isFavorite = false;
    private String languageTo;
    private String languageFrom;
    private List<String> languages = new ArrayList<>();
    private Map<String, String> languagesMap;
    private final String INPUT_TEXT_KEY = "input";
    private final String FROM_LANG_KEY = "from_lang";
    private final String TO_LANG_KEY = "to_lang";
    private final String FILE_NAME = "com.kovapss.translatenow.sharedPreferenses";
    private final int INPUT_TIMEOUT = 250;

    public MainFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logger.d("onCreateView");
        View view =  inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        initToolbar();
        initSpinners();
        resultText.setMovementMethod(ScrollingMovementMethod.getInstance());
        yandexText.setMovementMethod(LinkMovementMethod.getInstance());
        mCompositeDisposable = new CompositeDisposable();
        handleTextChanges();
        return view;
    }



    @Override
    public void handleTextChanges() {
        mCompositeDisposable.add(RxTextView.textChanges(inputText)
                .skipInitialValue()
                .debounce(INPUT_TIMEOUT, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .map(CharSequence::toString)
                .map(String::trim)
                .subscribe(s -> {
                    if (s.length() == 0){
                        resultLayout.setVisibility(View.INVISIBLE);
                        favoriteImage.setEnabled(false);
                        clearImage.setEnabled(false);
                        yandexText.setVisibility(View.INVISIBLE);
                        yandexText.setEnabled(false);
                    } else {
                        presenter.textChanged(s, getLanguagesDirection());
                    }
                }));
    }


    @Override
    public void showTranslate(Observable<String> observable) {
        mCompositeDisposable.add(
                observable.observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete(() -> {
                            resultLayout.setVisibility(View.VISIBLE);
                            favoriteImage.setEnabled(true);
                            clearImage.setEnabled(true);
                            yandexText.setVisibility(View.VISIBLE);
                            yandexText.setEnabled(true);
                        })
                        .subscribe(s -> resultText.setText(s))
        );
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(resultLayout, message, Snackbar.LENGTH_SHORT).show();
    }



    @Override
    public void setOfflineMode(boolean enabled) {
        if (enabled){
            Logger.d("Enabling offline mode");
            rootLayout.setEnabled(false);
            offlineLayout.setVisibility(View.VISIBLE);
            offlineLayout.setEnabled(true);
        } else {
            Logger.d("disabling offline mode");
            rootLayout.setEnabled(true);
            offlineLayout.setVisibility(View.INVISIBLE);
            offlineLayout.setEnabled(false);
        }

    }

    @Override
    public void showAvailableLanguages(Observable<AvailableLanguages> observable) {
        mCompositeDisposable.add(
                observable
                        .doOnNext(availableLanguages -> languages.addAll(availableLanguages.languages.values()))
                        .map(availableLanguages -> {
                            Map<String, String> reversedMap = new HashMap<>();
                            for (Map.Entry<String, String> entry : availableLanguages.languages.entrySet()){
                                reversedMap.put(entry.getValue(), entry.getKey());
                            }
                            return reversedMap;
                        })
                        .doOnNext(stringStringHashMap -> this.languagesMap = stringStringHashMap)
                        .doOnError(throwable -> showMessage("Error"))
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnComplete(() -> {
                            Collections.sort(languages, Collator.getInstance());
                            spinnerFrom.setItems(languages);
                            spinnerTo.setItems(languages);
                            returnState();
                        })
                        .subscribe()
        );
    }

    private void returnState() {
        SharedPreferences bundle = getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        inputText.setText(bundle.getString(INPUT_TEXT_KEY, ""));
        spinnerFrom.setSelectedIndex(bundle.getInt(FROM_LANG_KEY, 0));
        spinnerTo.setSelectedIndex(bundle.getInt(TO_LANG_KEY, 0));
        languageFrom = languagesMap.get(languages.get(spinnerFrom.getSelectedIndex()));
        languageTo =  languagesMap.get(languages.get(spinnerTo.getSelectedIndex()));
    }

    @Override
    public void clearInputText() {
        inputText.getText().clear();
    }

    @Override
    public void clearResultText() {
        resultText.setText("");
    }

    @Override
    public void changeImage() {
        if (!isFavorite){
            favoriteImage.setImageResource(R.drawable.ic_star_black_24dp);
            isFavorite = true;
        } else {
            favoriteImage.setImageResource(R.drawable.ic_star_border_black_32dp);
            isFavorite = false;
        }
    }


    @OnClick(R.id.addto_favorite_button_image)
    void onClickFavorite(){
        if (isFavorite){
            Logger.d("unFavorite");
            presenter.clickUnFavorite(inputText.getText().toString(),
                    resultText.getText().toString());
            isFavorite = false;
        } else {
            presenter.clickFavorite(inputText.getText().toString(),
                    resultText.getText().toString(), getLanguagesDirection());
            isFavorite = true;
        }

    }

    @OnClick(R.id.clear_action_image)
    void onClickClear(){
        presenter.clickClear();
    }

    @OnClick(R.id.update_button)
    void onClickUpdate(){
        presenter.clickUpdate();
    }


    @OnClick(R.id.langs_arrows)
    void onClickLanguagesArrows(){
        int var = spinnerFrom.getSelectedIndex();
        spinnerFrom.setSelectedIndex(spinnerTo.getSelectedIndex());
        spinnerTo.setSelectedIndex(var);
        languageFrom = languagesMap.get(languages.get(spinnerFrom.getSelectedIndex()));
        languageTo = languagesMap.get(languages.get(spinnerTo.getSelectedIndex()));
        presenter.languagesChanged(inputText.getText().toString(), getLanguagesDirection());
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.d("OnStop");
        mCompositeDisposable.clear();
        SharedPreferences.Editor editor = getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(INPUT_TEXT_KEY, inputText.getText().toString());
        editor.putInt(FROM_LANG_KEY, spinnerFrom.getSelectedIndex());
        editor.putInt(TO_LANG_KEY, spinnerTo.getSelectedIndex());
        editor.apply();
    }


    private void initSpinners() {
        spinnerFrom.setOnItemSelectedListener((view, position, id, item) -> {
            languageFrom = languagesMap.get(view.getText());
            Logger.d("From lang : " + languageFrom);
            presenter.languagesChanged(inputText.getText().toString(), getLanguagesDirection());
        });

        spinnerTo.setOnItemSelectedListener((view, position, id, item) -> {
            languageTo = languagesMap.get(view.getText());
            Logger.d("To lang : " + languageTo);
            presenter.languagesChanged(inputText.getText().toString(), getLanguagesDirection());
        });
    }

    private void initToolbar() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    private String getLanguagesDirection(){
        return languageFrom + "-" + languageTo;
    }


}
