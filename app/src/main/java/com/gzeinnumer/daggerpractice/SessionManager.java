package com.gzeinnumer.daggerpractice;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.gzeinnumer.daggerpractice.network.authApi.model.ResponseLogin;
import com.gzeinnumer.daggerpractice.ui.auth.AuthResource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {

    private static final String TAG = "SessionManager";
    private MediatorLiveData<AuthResource<ResponseLogin>> chacedUser = new MediatorLiveData<>();

    @Inject
    public SessionManager(){

    }

    public void authenticatedWithId(final LiveData<AuthResource<ResponseLogin>> source){
        if(chacedUser != null){
            chacedUser.setValue(AuthResource.loading((ResponseLogin)null));
            chacedUser.addSource(source, new Observer<AuthResource<ResponseLogin>>() {
                @Override
                public void onChanged(AuthResource<ResponseLogin> responseLoginAuthResource) {
                    chacedUser.setValue(responseLoginAuthResource);
                    chacedUser.removeSource(source);
                }
            });
        }
    }

    public void logOut(){
        Log.d(TAG, "logOut: logging out");
        chacedUser.setValue(AuthResource.<ResponseLogin>logout());
    }

    public LiveData<AuthResource<ResponseLogin>> getAuthUser(){
        return chacedUser;
    }
}
