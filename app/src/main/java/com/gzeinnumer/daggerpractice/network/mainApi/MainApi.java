package com.gzeinnumer.daggerpractice.network.mainApi;

import com.gzeinnumer.daggerpractice.network.mainApi.model.ResponsePost;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainApi {
    //https://jsonplaceholder.typicode.com/posts?userId=1
    @GET("posts")
    Flowable<List<ResponsePost>> getPotsFromUser(
            @Query("userId") int id
    );
}
