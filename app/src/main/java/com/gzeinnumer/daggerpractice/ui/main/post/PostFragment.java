package com.gzeinnumer.daggerpractice.ui.main.post;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gzeinnumer.daggerpractice.R;
import com.gzeinnumer.daggerpractice.network.mainApi.model.ResponsePost;
import com.gzeinnumer.daggerpractice.ui.main.MainResource;
import com.gzeinnumer.daggerpractice.vm.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends DaggerFragment {

    private static final String TAG = "PostFragment";

    public PostFragment() {
        // Required empty public constructor
    }

    private PostVM viewModel;
    private RecyclerView recyclerView;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view);

        Log.d(TAG, "onViewCreated: ");

        viewModel = ViewModelProviders.of(this, providerFactory).get(PostVM.class);

        subscribeObservers();
    }

    private void subscribeObservers() {
        viewModel.observePosts().removeObservers(getViewLifecycleOwner());
        viewModel.observePosts().observe(getViewLifecycleOwner(), new Observer<MainResource<List<ResponsePost>>>() {
            @Override
            public void onChanged(MainResource<List<ResponsePost>> listMainResource) {
                if(listMainResource != null){
                    Log.d(TAG, "onChanged: "+listMainResource.data);
                }
            }
        });
    }
}
