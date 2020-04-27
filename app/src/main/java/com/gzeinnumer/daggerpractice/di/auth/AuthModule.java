package com.gzeinnumer.daggerpractice.di.auth;

import com.gzeinnumer.daggerpractice.network.authApi.AuthApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AuthModule {
    @Provides
    static AuthApi providesAuthApi(Retrofit retrofit){
        return retrofit.create(AuthApi.class);
    }
}
