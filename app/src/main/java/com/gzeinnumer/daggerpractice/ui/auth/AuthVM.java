package com.gzeinnumer.daggerpractice.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

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


    @Inject
    AuthVM(AuthApi authApi){
        this.authApi = authApi;
        Log.d(TAG, "AuthVM: viewmodel sudah bekerja");
        if(this.authApi == null){
            Log.d(TAG, "AuthVM: api is NULL");
        } else {
            Log.d(TAG, "AuthVM: api is not NULL");
        }
//
//        authApi.getUser(1).toObservable().subscribeOn(Schedulers.io()).
//                subscribe(new Observer<AuthResource<ResponseLogin>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(AuthResource<ResponseLogin> responseLoginAuthResource) {
//                        Log.d(TAG, "onNext: "+ responseLoginAuthResource.data.getEmail());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "onError: "+e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }

    private MediatorLiveData<AuthResource<ResponseLogin>> authUser = new MediatorLiveData<>();
    void authWithId(final int userId){

        authUser.setValue(AuthResource.loading((ResponseLogin) null));

        final LiveData<AuthResource<ResponseLogin>> source =LiveDataReactiveStreams.fromPublisher(
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

        authUser.addSource(source, new Observer<AuthResource<ResponseLogin>>() {
            @Override
            public void onChanged(AuthResource<ResponseLogin> responseLoginAuthResource) {
                authUser.setValue(responseLoginAuthResource);
                authUser.removeSource(source);
            }
        });
    }

    LiveData<AuthResource<ResponseLogin>> observeUser(){
        return authUser;
    }
}
