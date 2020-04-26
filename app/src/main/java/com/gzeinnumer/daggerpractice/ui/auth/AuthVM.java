package com.gzeinnumer.daggerpractice.ui.auth;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

public class AuthVM extends ViewModel {

    private static final String TAG = "AuthVM";
    @Inject
    AuthVM(){
        Log.d(TAG, "AuthVM: viewmodel sudah bekerja");
    }
}
