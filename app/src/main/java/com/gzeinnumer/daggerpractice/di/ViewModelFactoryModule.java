package com.gzeinnumer.daggerpractice.di;

import androidx.lifecycle.ViewModelProvider;

import com.gzeinnumer.daggerpractice.vm.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
abstract class ViewModelFactoryModule {
    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory modelProviderFactory);
}
