package com.gzeinnumer.daggerpractice.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.gzeinnumer.daggerpractice.SessionManager;
import com.gzeinnumer.daggerpractice.network.authApi.AuthApi;
import com.gzeinnumer.daggerpractice.network.authApi.model.ResponseLogin;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AuthVM extends ViewModel {

    private static final String TAG = "AuthVM";
    //ingat object yang ada didalam function yang di inject, itu sudah ada di @Provide Module
    private AuthApi authApi;
    private SessionManager sessionManager;


    @Inject
    AuthVM(AuthApi authApi, SessionManager sessionManager){
        this.authApi = authApi;
        this.sessionManager = sessionManager;
        Log.d(TAG, "AuthVM: viewmodel sudah bekerja");
        if(this.authApi == null){
            Log.d(TAG, "AuthVM: api is NULL");
        } else {
            Log.d(TAG, "AuthVM: api is not NULL");
        }
    }

//    private MediatorLiveData<AuthResource<ResponseLogin>> authUser = new MediatorLiveData<>();
    void authWithId(final int userId){
        Log.d(TAG, "authWithId: attempting to login");
        sessionManager.authenticatedWithId(queryUserId(userId));
    }

    LiveData<AuthResource<ResponseLogin>> observeAuthState(){
        return sessionManager.getAuthUser();
    }

    private LiveData<AuthResource<ResponseLogin>> queryUserId(int userId){
        return LiveDataReactiveStreams.fromPublisher(
                authApi.getUser(userId)
                        .onErrorReturn(new Function<Throwable, ResponseLogin>() {
                            @Override
                            public ResponseLogin apply(Throwable throwable) throws Exception {
                                ResponseLogin responseLogin = new ResponseLogin();
                                responseLogin.setId(-1);
                                return responseLogin;
                            }
                        })
                        .map(new Function<ResponseLogin, AuthResource<ResponseLogin>>() {
                            @Override
                            public AuthResource<ResponseLogin> apply(ResponseLogin responseLogin) throws Exception {
                                if(responseLogin.getId() == -1){
                                    return AuthResource.error("Could not aurhenticate");
                                }
                                return AuthResource.authenticated(responseLogin);
                            }
                        })
                        .subscribeOn(Schedulers.io())
        );
    }
}
