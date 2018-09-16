package com.movi.global;

import android.content.Context;

public interface BaseView {

    void showLoader();

    void hideLoader();

    void showError(String msg);

    Context getContextAppp();

    void onBackPress();
}
