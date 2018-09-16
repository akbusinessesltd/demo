package com.movi.di.module;

import com.movi.model.AppInterface;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {NetModule.class})
public class APIModule {

    @Provides
    @Singleton
    public AppInterface provideAppAPI(Retrofit retrofit){
        return retrofit.create(AppInterface.class);
    }
}
