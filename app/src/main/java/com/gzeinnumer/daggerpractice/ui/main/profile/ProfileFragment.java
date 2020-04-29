package com.gzeinnumer.daggerpractice.ui.main.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gzeinnumer.daggerpractice.R;
import com.gzeinnumer.daggerpractice.network.authApi.model.ResponseLogin;
import com.gzeinnumer.daggerpractice.ui.auth.AuthResource;
import com.gzeinnumer.daggerpractice.vm.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends DaggerFragment {

    private static final String TAG = "ProfileFragment";

    public ProfileFragment() {
        // Required empty public constructor
    }

    private TextView email, username, website;
    private ProfileVM viewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: GZeinNumer");
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ProfileFragment was created");

        email = view.findViewById(R.id.email);
        username = view.findViewById(R.id.username);
        website = view.findViewById(R.id.website);

        viewModel = ViewModelProviders.of(this, providerFactory).get(ProfileVM.class);
        subscribeObservers();
    }

    private void subscribeObservers(){
        viewModel.getAuthenticationUser().removeObservers(getViewLifecycleOwner());
        viewModel.getAuthenticationUser().observe(getViewLifecycleOwner(), new Observer<AuthResource<ResponseLogin>>() {
            @Override
            public void onChanged(AuthResource<ResponseLogin> responseLoginAuthResource) {
                if(responseLoginAuthResource != null){
                    switch (responseLoginAuthResource.status){
                        case AUTHENTICATED:
                            setUserDetails(responseLoginAuthResource.data);
                            break;
                        case ERROR:
                            setErrorDetails(responseLoginAuthResource.message);
                            break;
                    }
                }
            }
        });
    }

    private void setUserDetails(ResponseLogin data) {
        email.setText(data.getEmail());
        username.setText(data.getUsername());
        website.setText(data.getWebsite());
    }

    private void setErrorDetails(String message) {
        email.setText(message);
        username.setText("error");
        website.setText("error");
    }
}
