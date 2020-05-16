package com.gzeinnumer.daggerpractice.di.auth;

import com.gzeinnumer.daggerpractice.di.Named;
import com.gzeinnumer.daggerpractice.network.authApi.AuthApi;
import com.gzeinnumer.daggerpractice.network.authApi.model.ResponseLogin;
import com.gzeinnumer.daggerpractice.ui.main.MainActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class AuthModule {
    @AuthScope
    @Provides
    static AuthApi providesAuthApi(Retrofit retrofit){
        return retrofit.create(AuthApi.class);
    }

//    scope-example
//    disini memori akan di create ulang
//    @Named untuk membuat 2 buah @Provides yang me return hal yang sama, kita butuh menggunakan @Named
//    dan gunakan @Named dinawah @Inject, lihat di MainActivity
    @AuthScope
    @Provides
    @Named("auth_login")
    static ResponseLogin responseLogin2(){
        return new ResponseLogin();
    }
}
