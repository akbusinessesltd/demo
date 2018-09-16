package com.movi.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.movi.R;
import com.movi.adapter.ListAdapter;
import com.movi.global.CommonUtils;
import com.movi.model.Response;
import com.movi.ui.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.facebook.ads.AdSettings.addTestDevice;
import static com.movi.R.id.rvList;

public class MainActivity extends BaseActivity implements MainView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MainActivity";
    @BindView(rvList)
    RecyclerView recyclerView;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tvNoData)
    TextView tvNoData;
	// datalist
    private List<Response> dataList = new ArrayList<>();
    private ListAdapter adapter;
    private MainPresenter presenter;
	// facebook ads
    private AdRequest adRequest;
    private boolean isVisible = false;
    // facebook
    private com.facebook.ads.InterstitialAd interstitialAdFb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        init();
    }

	// first init
    private void init() {
        presenter = new MainPresenterImpl(this);
        addData();
        dataList.clear();
        presenter.dataListAPI();

        adapter = new ListAdapter(MainActivity.this, dataList, requestManager);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setAdapter(adapter);

        if (sharePref.getBoolean(CommonUtils.ERROR_IN_LOAD)) {
            addTestDevice("f40b4a01fc1f3c8006de0e82573d3efc");
            showFacebookAds();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    addTestDevice("f40b4a01fc1f3c8006de0e82573d3efc");
                    showFacebookAds();
                }
            }, 5000);
        }
    }

    @OnClick(R.id.ivShare)
    void onClickShare() {
	   // share text into messages app
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_name) + getString(R.string.share_text) + getApplication().getPackageName());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override
    public void showError(String msg) {
	 // show error message here
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        presenter.dataListAPI();
                    }
                });
        snackbar.show();

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();

    }

    @Override
    public void successResponse(List<Response> list) {
        dataList.clear();
        dataList.addAll(list);

        if (dataList.isEmpty()) {
            tvNoData.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.GONE);
            return;
        }

        tvNoData.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.VISIBLE);

        Collections.reverse(dataList);
        for (int i1 = 0; i1 < dataList.size(); i1++) {
            if (i1 > 1 && i1 % 5 == 0) {
                Response model = new Response("", "");
                dataList.add(i1, model);
            }
        }
        for (int i = 0; i < dataList.size(); i++) {
            Log.e(TAG, "successResponse: " + dataList.get(i).getName());
        }

        if (adapter != null)
            adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        isVisible = false;
        super.onPause();
        presenter.cancelApiCall();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isVisible = true;
    }

    @Override
    public void showLoader() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoader() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        presenter.dataListAPI();
    }

    private void addData() {
        dataList.add(new Response("", "jl5Ysdfe34D"));
        dataList.add(new Response("", "plkSD34saad"));
        dataList.add(new Response("", "Wssad5455aa"));
        dataList.add(new Response("", "Va-dae85daa"));
        dataList.add(new Response("", "Hrs-srk3245"));
        dataList.add(new Response("", "lFb0aoadEFB"));
    }

    private void showFacebookAds() {
        interstitialAdFb = new com.facebook.ads.InterstitialAd(this, getString(R.string.fb_interstitial_ads));
        interstitialAdFb.setAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                Log.e(TAG, "onError: "+ad.getPlacementId() );
            }

            @Override
            public void onError(Ad ad, AdError adError) {

                Log.e(TAG, "onError: "+adError.getErrorMessage() );

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