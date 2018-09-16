package com.movi.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.movi.MyApp;
import com.movi.global.BaseView;
import com.movi.global.CommonUtils;
import com.movi.global.SharePref;
import com.movi.model.AppInterface;

import javax.inject.Inject;

public class BaseActivity extends AppCompatActivity  implements BaseView {

    @Inject
    public Gson gson; // gson object
    @Inject
    public RequestManager requestManager; //glide
    @Inject
    public CommonUtils commonUtils; 
    @Inject
    public AppInterface appAPI; // API call
    @Inject 
    public SharePref sharePref; // Shareprefrence 



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApp) getApplication()).getmNetComponent().inject(this);


    }

    @Override
    public void showLoader() {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public void showError(String msg) {

    }


    @Override
    public Context getContextAppp() {
        return BaseActivity.this;
    }

    @Override
    public void onBackPress() {

    }
}
