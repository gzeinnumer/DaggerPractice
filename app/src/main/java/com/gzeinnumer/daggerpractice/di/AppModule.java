package com.gzeinnumer.daggerpractice.di;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.gzeinnumer.daggerpractice.R;
import com.gzeinnumer.daggerpractice.network.authApi.model.ResponseLogin;
import com.gzeinnumer.daggerpractice.ui.main.MainActivity;
import com.gzeinnumer.daggerpractice.util.Constant;

import com.gzeinnumer.daggerpractice.di.Named;
import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
class AppModule {

    @Singleton
    @Provides
    static String someString(){
        return "GZeinNumer";
    }

    @Singleton
    @Provides
    static RequestOptions providesRequestOptions(){
        return RequestOptions.placeholderOf(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher);
    }

    @Singleton
    @Provides
    static RequestManager providesRequestManager(Application application, RequestOptions requestOptions){
        return Glide.with(application)
                .setDefaultRequestOptions(requestOptions);
    }

    @Singleton
    @Provides
    static Drawable providesDrawable(Application application){
        return ContextCompat.getDrawable(application, R.mipmap.ic_launcher);
    }

    @Singleton
    @Provides
    static Retrofit providesRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

//    scope-example
//    disini memori tidak akan di create ulang
//    @Named untuk membuat 2 buah @Provides yang me return hal yang sama, kita butuh menggunakan @Named
//    dan gunakan @Named dinawah @Inject, lihat di MainActivity
    @Singleton
    @Provides
    @Named("app_login")
    static ResponseLogin responseLogin1(){
        return new ResponseLogin();
    }
}
