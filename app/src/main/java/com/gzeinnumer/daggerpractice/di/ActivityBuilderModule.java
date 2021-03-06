package com.gzeinnumer.daggerpractice.di;

import com.gzeinnumer.daggerpractice.di.auth.AuthModule;
import com.gzeinnumer.daggerpractice.di.auth.AuthScope;
import com.gzeinnumer.daggerpractice.di.auth.AuthViewModelsModule;
import com.gzeinnumer.daggerpractice.di.main.MainFragmentBuilderModule;
import com.gzeinnumer.daggerpractice.di.main.MainModule;
import com.gzeinnumer.daggerpractice.di.main.MainScope;
import com.gzeinnumer.daggerpractice.di.main.MainViewModelsModule;
import com.gzeinnumer.daggerpractice.ui.auth.AuthActivity;
import com.gzeinnumer.daggerpractice.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ActivityBuilderModule {
    //catatan penting
    /**
     * setelah ini @ContributesAndroidInjector akan berubah, sebelum berubah
     * tujuan kodingan tampa SubComponent, setiap ViewModel di pakage bisa di akses oleh siapapun activitynya,
     * MainVM diakses oleh MainActivity, SecondVM diakses oleh SecondActivity, ThirdVM diakses oleh ThirdActivity
     * tapi, tampa sub komponent ThirdActivity bisa mengakses SecondVM, MainVM, dan ThirdVM sekaligus,
     * bagaimana caranya agar tidak bsa di akses? caranya adalah dengan memakai subcomponent,
     * memastikan hanya MainActivity, yang bisa mengakses MainVM
     */
    //for all
//    @ContributesAndroidInjector
//    abstract AuthActivity constributeAuthActivity();
    //for u only

    @AuthScope
    @ContributesAndroidInjector(
            modules = {AuthViewModelsModule.class, AuthModule.class}
    )
    abstract AuthActivity constributeAuthActivity();

    @MainScope
    @ContributesAndroidInjector(
            modules = {MainFragmentBuilderModule.class, MainViewModelsModule.class, MainModule.class}
    )
    abstract MainActivity constributeMainActivity();
}
