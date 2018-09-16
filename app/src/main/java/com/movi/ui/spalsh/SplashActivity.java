package com.movi.ui.spalsh;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.movi.R;
import com.movi.global.CommonUtils;
import com.movi.ui.BaseActivity;
import com.movi.ui.home.MainActivity;

public class SplashActivity extends BaseActivity implements SplashView {
    private static final String TAG = "SplashActivity";
    private Runnable runnable;
    private Handler handler;
    private InterstitialAd mInterstitialAd;
    private SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
		// remove callbacks handler
        handler.removeCallbacks(runnable);
        presenter.cancelApiCall();
    }


    private void init() {
        // Initializing video player with developer key
        presenter = new SplashPresenterImpl(this);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                setupGoogleAdMob();
            }
        };
		// start timer 0.5 sec
        handler.postDelayed(runnable, 500);
    }

	// setup google ads
    private void setupGoogleAdMob() {
        AdRequest adRequest = new AdRequest.Builder().build();

        mInterstitialAd = new InterstitialAd(this);

        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                sharePref.setBoolean(CommonUtils.ERROR_IN_LOAD, false);
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                Log.e(TAG, "onAdFailedToLoad: " );
                sharePref.setBoolean(CommonUtils.ERROR_IN_LOAD, true);
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
                super.onAdFailedToLoad(i);
            }

            public void onAdLoaded() {
                showInterstitial();
            }
        });
    }

    private void showInterstitial() {
        finish();
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    public void successResponse() {
        setupGoogleAdMob();
    }
}
