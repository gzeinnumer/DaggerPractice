package com.gzeinnumer.daggerpractice.di.main;

import androidx.lifecycle.ViewModel;

import com.gzeinnumer.daggerpractice.di.ViewModelKey;
import com.gzeinnumer.daggerpractice.ui.main.profile.ProfileVM;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileVM.class)
    public abstract ViewModel bidProfileViewModel(ProfileVM profileVM);
}
