package com.movi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.movi.R;
import com.movi.global.SharePref;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.ads.AdSettings.addTestDevice;

public class DetailsActivity extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {
    private static final String TAG = "DetailsActivity";
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    @BindView(R.id.ivMainImage)
    ImageView ivMainImage;
    @BindView(R.id.youtube_view)
    YouTubePlayerView youtubeView;
    @BindView(R.id.activity_details)
    LinearLayout activityDetails;

    private String yID = "";
    private InterstitialAd mInterstitialAd;
    private boolean isVisible = false;
    private Runnable runnable;
    private Handler handler;
    private AdRequest adRequest;
    private SharePref sharePref;
    private com.facebook.ads.InterstitialAd interstitialAdFb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        sharePref = new SharePref(DetailsActivity.this);
        // Initializing video player with developer key
        youtubeView.initialize(getString(R.string.youtube_key), this);
        yID = getIntent().getStringExtra("yID");
        showFacebookAds();
    }

    private void setupGoogleAdMob() {

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });
    }

    private void showInterstitial() {
        if (isVisible)
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isVisible = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isVisible = false;
        if (handler != null)
            handler.removeCallbacks(runnable);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if (!wasRestored) {

            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            youTubePlayer.loadVideo(yID);
//            youTubePlayer.cueVideo(yID);

            // Hiding player controls
            youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            youtubeView.initialize(getString(R.string.youtube_key), this);
        }
    }

    private void showFacebookAds() {


        addTestDevice("f40b4a01fc1f3c8006de0e82573d3efc");
        interstitialAdFb = new com.facebook.ads.InterstitialAd(this, getString(R.string.fb_interstitial_ads));
        interstitialAdFb.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e(TAG, "onError: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                interstitialAdFb.show();
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {


            }
        });
        interstitialAdFb.loadAd();

    }


}
