package com.movi.di.module;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.movi.global.CommonUtils;
import com.movi.global.SharePref;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module(includes = AppModule.class)
public class NetModule {
    String mBaseUrl;

    public NetModule(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

    @Provides
    @Singleton
    RequestManager provideGlide(Application application){
        return  Glide.with(application);
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }
    @Provides
    @Singleton
    CommonUtils provideCommonUtils(Application application){
        return new CommonUtils(application);
    }


    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {
        OkHttpClient.Builder okClient = new OkHttpClient.Builder();
        okClient.connectTimeout(60000, TimeUnit.MILLISECONDS);
        okClient.writeTimeout(60000, TimeUnit.MILLISECONDS);
        okClient.readTimeout(60000, TimeUnit.MILLISECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        okClient.interceptors().add(interceptor);

        okClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                response.header("Content-Type", "application/json");
                response.newBuilder()
                        .build();
                return response;
            }
        });
        return okClient.build();
    }


    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    SharePref provideSharePref(Application application) {
        return new SharePref(application);
    }


}
