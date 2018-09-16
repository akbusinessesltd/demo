package com.movi;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.movi.di.component.DaggerNetComponent;
import com.movi.di.component.NetComponent;
import com.movi.di.module.AppModule;
import com.movi.di.module.NetModule;


public class MyApp extends Application{
    private static MyApp  mObject;

    public NetComponent getmNetComponent() {
        return mNetComponent;
    }

    public NetComponent mNetComponent;

    public static MyApp getInstance() {
        return mObject;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mObject = this;
        init();
    }

    private void init() {
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(getString(R.string.mov_url_start)))
                .build();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
