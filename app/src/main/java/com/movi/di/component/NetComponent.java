package com.movi.di.component;


import com.movi.di.module.APIModule;
import com.movi.di.module.AppModule;
import com.movi.di.module.NetModule;
import com.movi.global.BaseUseCaseImpl;
import com.movi.ui.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {AppModule.class, NetModule.class,  APIModule.class})
public interface NetComponent {
    void inject(BaseActivity mainActivity);
    void inject(BaseUseCaseImpl baseUseCase);
}
