package com.gzeinnumer.daggerpractice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.gzeinnumer.daggerpractice.network.authApi.model.ResponseLogin;
import com.gzeinnumer.daggerpractice.ui.auth.AuthActivity;
import com.gzeinnumer.daggerpractice.ui.auth.AuthResource;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {
    private static final String TAG = "BaseActivity";

    @Inject
    protected SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "BaseActivity", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate: created");
        subscribeObservers();
    }

    private void subscribeObservers(){
        sessionManager.getAuthUser().observe(this, new Observer<AuthResource<ResponseLogin>>() {
            @Override
            public void onChanged(AuthResource<ResponseLogin> responseLoginAuthResource) {
                if(responseLoginAuthResource != null){
                    switch (responseLoginAuthResource.status){
                        case LOADING:
                            break;
                        case AUTHENTICATED:
                            Log.d(TAG, "onChanged: Login AUTHENTICATED "+responseLoginAuthResource.data.getEmail());
                            break;
                        case ERROR:
                            Log.d(TAG, "onChanged: Login ERROR " + responseLoginAuthResource.message + " Only 1-10 Number avaliable");
                            break;
                        case NOT_AUTHENTICATED:
                            navLoginScreen();
                            break;
                    }
                }
            }
        });
    }

    private void navLoginScreen(){
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }
}
