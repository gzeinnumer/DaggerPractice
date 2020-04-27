package com.gzeinnumer.daggerpractice.ui.auth;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.gzeinnumer.daggerpractice.network.authApi.AuthApi;

import javax.inject.Inject;

public class AuthVM extends ViewModel {

    private static final String TAG = "AuthVM";
    //ingat object yang ada didalam function yang di inject, itu sudah ada di @Provide Module
    private AuthApi authApi;
    @Inject
    AuthVM(AuthApi authApi){
        this.authApi = authApi;
        Log.d(TAG, "AuthVM: viewmodel sudah bekerja");
        if(this.authApi == null){
            Log.d(TAG, "AuthVM: api is NULL");
        } else {
            Log.d(TAG, "AuthVM: api is not NULL");
        }
    }
}
