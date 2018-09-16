package com.movi.ui.spalsh;

import com.movi.R;
import com.movi.global.BaseUseCaseImpl;
import com.movi.global.CommonUtils;
import com.movi.model.ads.ADSModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SplashPresenterImpl extends BaseUseCaseImpl implements SplashPresenter {

    private SplashView splashView;
    private Call<ADSModel> call;

    public SplashPresenterImpl(SplashView splashView) {
        super(splashView);
        this.splashView = (SplashView) splashView;
    }

    @Override
    public void getAdsList() {
        if (commonUtils.isNetworkAvailable()) {
            call = appAPI.getADSData(getContext().getString(R.string.mov_id));
            call.enqueue(new Callback<ADSModel>() {
                @Override
                public void onResponse(Call<ADSModel> call, Response<ADSModel> response) {
                    if (response.isSuccessful()) {

                        ADSModel model = response.body();
                        if (model.isStatus()) {
                            if (!model.getResponse().isEmpty()) {
                                sharePref.setString(CommonUtils.BANNER_ADS, model.getResponse().get(0).getBanner() + "");
                                sharePref.setString(CommonUtils.BIG_ADS, model.getResponse().get(0).getBig() + "");
                                sharePref.setString(CommonUtils.NATIVE_ADS, model.getResponse().get(0).getNative() + "");
                            }
                            splashView.successResponse();
                        } else {
                            splashView.showError(getContext().getString(R.string.other_error));
                        }
                    }
                }

                @Override
                public void onFailure(Call<ADSModel> call, Throwable t) {
                    if (splashView != null) {
                        splashView.showError(getContext().getString(R.string.other_error));
                    }
                }
            });
        } else {
            if (splashView != null)
                splashView.showError(getContext().getString(R.string.internet_connection_error));
        }
    }

    @Override
    public void cancelApiCall() {
        if (call != null)
            call.cancel();
    }
}
