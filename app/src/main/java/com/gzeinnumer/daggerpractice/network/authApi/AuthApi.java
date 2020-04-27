package com.gzeinnumer.daggerpractice.network.authApi;

import com.gzeinnumer.daggerpractice.network.authApi.model.ResponseLogin;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AuthApi {
    @GET
    Call<ResponseLogin> getFakeStuff();
}
