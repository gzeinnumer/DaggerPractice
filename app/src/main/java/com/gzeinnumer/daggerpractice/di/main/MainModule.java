package com.gzeinnumer.daggerpractice.di.main;

import com.gzeinnumer.daggerpractice.network.mainApi.MainApi;
import com.gzeinnumer.daggerpractice.ui.main.post.PostsRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainModule {
    @MainScope
    @Provides
    static MainApi providesMainApi(Retrofit retrofit){
        return retrofit.create(MainApi.class);
    }

    @MainScope
    @Provides
    static PostsRecyclerAdapter providesRecyclerAdapter(){
        return new PostsRecyclerAdapter();
    }
}
