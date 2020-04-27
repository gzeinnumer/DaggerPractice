package com.gzeinnumer.daggerpractice.network.authApi;

import com.gzeinnumer.daggerpractice.network.authApi.model.ResponseLogin;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AuthApi {
    @GET("/users/{id}")
    Flowable<ResponseLogin> getUser(@Path("id") int id);
}
