package com.movi.global;

import android.content.Context;
import android.content.SharedPreferences;

import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.movi.MyApp;
import com.movi.di.component.NetComponent;
import com.movi.model.AppInterface;

import javax.inject.Inject;

public abstract class BaseUseCaseImpl implements BaseUseCase {

    private final BaseView mBaseView;

    @Inject
    public SharedPreferences sharedPreferences;
    @Inject
    public Gson gson;
    @Inject
    public RequestManager requestManager; //glide
    @Inject
    public CommonUtils commonUtils;
    @Inject
    public AppInterface appAPI;
    @Inject
    public SharePref sharePref;


    public BaseUseCaseImpl(BaseView baseView) {
        NetComponent injector = MyApp.getInstance().getmNetComponent();
        injector.inject(this);
        this.mBaseView = baseView;
    }


    @Override
    public void showLoader() {
        mBaseView.showLoader();
    }

    @Override
    public void hideLoader() {
        mBaseView.hideLoader();
    }

    @Override
    public Context getContext() {
        return mBaseView.getContextAppp();
    }

    @Override
    public void onBackPress() {
        mBaseView.onBackPress();
    }

}
