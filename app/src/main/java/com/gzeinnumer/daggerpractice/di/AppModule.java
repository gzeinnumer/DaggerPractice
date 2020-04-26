package com.gzeinnumer.daggerpractice.di;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.gzeinnumer.daggerpractice.R;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
}
