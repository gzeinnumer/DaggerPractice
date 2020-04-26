package com.gzeinnumer.daggerpractice.ui.auth;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.gzeinnumer.daggerpractice.R;
import com.gzeinnumer.daggerpractice.vm.ViewModelProviderFactory;

import javax.inject.Inject;
import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {

    private static final String TAG = "AuthActivity";

    @Inject
    String str;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    @Inject
    ViewModelProviderFactory providerFactory;
    private AuthVM viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        Log.d(TAG, "onCreate: "+ str);

        setLogo();

        viewModel = ViewModelProviders.of(this, providerFactory).get(AuthVM.class);
    }

    private void setLogo(){
        requestManager.load(logo).into((ImageView) findViewById(R.id.login_logo));
    }
}
