package com.gzeinnumer.daggerpractice.di.auth;

import androidx.lifecycle.ViewModel;

import com.gzeinnumer.daggerpractice.di.ViewModelKey;
import com.gzeinnumer.daggerpractice.ui.auth.AuthVM;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthVM.class)
    public abstract ViewModel bindAuthViewModel(AuthVM viewModel);

//    @Binds
//    @IntoMap
//    @ViewModelKey(MainVM.class)
//    public abstract ViewModel bindMainViewModel(MainVM viewModel);
    //untuk menggunakan 2 VM atau labih VM, bisa tambahkan disini, VM yang dipakia untuk mengganti Bundle
}
