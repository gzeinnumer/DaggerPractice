package com.gzeinnumer.daggerpractice.di.main;

import com.gzeinnumer.daggerpractice.network.mainApi.MainApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {
    @Provides
    static MainApi providesMainApi(Retrofit retrofit){
        return retrofit.create(MainApi.class);
    }
}
