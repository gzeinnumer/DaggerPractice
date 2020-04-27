package com.gzeinnumer.daggerpractice.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.gzeinnumer.daggerpractice.network.authApi.AuthApi;
import com.gzeinnumer.daggerpractice.network.authApi.model.ResponseLogin;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
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

        authApi.getUser(1).toObservable().subscribeOn(Schedulers.io()).
                subscribe(new Observer<ResponseLogin>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseLogin responseLogin) {
                        Log.d(TAG, "onNext: "+ responseLogin.getEmail());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+ e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private MediatorLiveData<ResponseLogin> authUser = new MediatorLiveData<>();
    void authWithId(int userId){
        final LiveData<ResponseLogin> source =LiveDataReactiveStreams.fromPublisher(
                authApi.getUser(userId)
                .subscribeOn(Schedulers.io())
        );

        authUser.addSource(source, new androidx.lifecycle.Observer<ResponseLogin>() {
            @Override
            public void onChanged(ResponseLogin responseLogin) {
                authUser.setValue(responseLogin);
                authUser.removeSource(source);
            }
        });
    }

    LiveData<ResponseLogin> observeUser(){
        return authUser;
    }
}
