package com.gzeinnumer.daggerpractice.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.gzeinnumer.daggerpractice.SessionManager;
import com.gzeinnumer.daggerpractice.network.authApi.model.ResponseLogin;
import com.gzeinnumer.daggerpractice.ui.auth.AuthResource;

import javax.inject.Inject;

public class ProfileVM extends ViewModel {
    private static final String TAG = "ProfileVM";

    private final SessionManager sessionManager;

    @Inject
    ProfileVM(SessionManager sessionManager){
        this.sessionManager = sessionManager;
        Log.d(TAG, "ProfileVM: ready...");
    }

    LiveData<AuthResource<ResponseLogin>> getAuthenticationUser(){
        return sessionManager.getAuthUser();
    }
}
