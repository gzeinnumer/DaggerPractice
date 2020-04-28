package com.gzeinnumer.daggerpractice.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

public class ProfileVM extends ViewModel {
    private static final String TAG = "ProfileVM";

    @Inject
    public ProfileVM(){
        Log.d(TAG, "ProfileVM: ready...");
    }
}
