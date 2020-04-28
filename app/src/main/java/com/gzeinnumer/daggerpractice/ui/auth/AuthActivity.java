package com.gzeinnumer.daggerpractice.ui.auth;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;
import com.gzeinnumer.daggerpractice.R;
import com.gzeinnumer.daggerpractice.network.authApi.model.ResponseLogin;
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

        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userId = findViewById(R.id.user_id_input);
                if(TextUtils.isEmpty(userId.getText().toString())){
                    return;
                }
                viewModel.authWithId(Integer.parseInt(userId.getText().toString()));
            }
        });

        subcribeObservers();
    }

    private void setLogo(){
        requestManager.load(logo).into((ImageView) findViewById(R.id.login_logo));
    }

    private ProgressBar progressBar;
    private void showLoading(Boolean isVisible){
        if(isVisible){
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
    private void subcribeObservers() {
        progressBar = findViewById(R.id.progress_bar);
        viewModel.observeUser().observe(this, new Observer<AuthResource<ResponseLogin>>() {
            @Override
            public void onChanged(AuthResource<ResponseLogin> responseLoginAuthResource) {
                if(responseLoginAuthResource != null){
                    switch (responseLoginAuthResource.status){
                        case LOADING:
                            showLoading(true);
                            break;
                        case AUTHENTICATED:
                            showLoading(false);
                            Log.d(TAG, "onChanged: Login AUTHENTICATED "+responseLoginAuthResource.data.getEmail());
                            break;
                        case ERROR:
                            showLoading(false);
                            Log.d(TAG, "onChanged: Login ERROR " + responseLoginAuthResource.message + " Only 1-10 Number avaliable");
                            break;
                        case NOT_AUTHENTICATED:
                            showLoading(false);
                            break;
                    }
                }
            }
        });
    }
}
