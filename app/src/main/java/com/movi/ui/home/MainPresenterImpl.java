package com.movi.ui.home;

import com.movi.R;
import com.movi.global.BaseUseCaseImpl;
import com.movi.model.VideoModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainPresenterImpl extends BaseUseCaseImpl implements MainPresenter {

    private MainView mainView;
    private Call<VideoModel> call;

    public MainPresenterImpl(MainView mainView) {
        super(mainView);
        this.mainView = (MainView) mainView;
    }

    @Override
    public void dataListAPI() {
        if (commonUtils.isNetworkAvailable()) {
            if (mainView != null)
                mainView.showLoader();

            call = appAPI.getData(getContext().getString(R.string.mov_id));
            call.enqueue(new Callback<VideoModel>() {
                @Override
                public void onResponse(Call<VideoModel> call, Response<VideoModel> response) {
                    if (mainView != null)
                        mainView.hideLoader();

                    if (response.isSuccessful()) {
                        VideoModel model = response.body();
                        if (model.isStatus()) {
                            mainView.successResponse(model.getResponse());
                        } else {
                            mainView.showError(getContext().getString(R.string.other_error));
                        }
                    }
                }

                @Override
                public void onFailure(Call<VideoModel> call, Throwable t) {
                    if (mainView != null) {
                        mainView.hideLoader();
                        mainView.showError(getContext().getString(R.string.other_error));
                    }
                }
            });
        } else {
            if (mainView != null)
                mainView.showError(getContext().getString(R.string.internet_connection_error));
        }
    }

    @Override
    public void cancelApiCall() {
        if (call != null)
            call.cancel();
    }

}
