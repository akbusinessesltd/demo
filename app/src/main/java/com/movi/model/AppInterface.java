package com.movi.model;

import com.movi.model.ads.ADSModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AppInterface {

    @GET("/service/mov_details.php")
    Call<VideoModel> getData(@Query("mov_id") String mov_id);

    @GET("/service/ads_details.php")
    Call<ADSModel> getADSData(@Query("mov_id") String mov_id);

}